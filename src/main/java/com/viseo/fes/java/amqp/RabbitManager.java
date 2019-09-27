package com.viseo.fes.java.amqp;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import org.apache.logging.log4j.ThreadContext;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RabbitManager {
    private static Logger log = LoggerFactory.getLogger(RabbitManager.class);
    private final Connection conn;

    public RabbitManager(String uri) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException {
        // simple rabbitmq connection
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(uri);
        conn = factory.newConnection();
    }

    public void start(String packageName) throws Exception {
        // load all AmqpController
        Reflections reflections = new Reflections(packageName);
        for (Class<?> controller : reflections.getTypesAnnotatedWith(AmqpController.class)) {
            load(controller);
        }

        new CompletableFuture<>().get();
    }

    /**
     * Here we:
     * - instantiate clazz
     * - create a proxy to authorization purpose {@link YouShallNotPass} purpose
     * - for each {@link Handler} method of clazz, we create an {@link ConsumerAdapter}
     *
     * The {@link ConsumerAdapter} will:
     * - make the authentication (search who send the message from headers of message)
     * - make deserialize body in String
     * - (n)ack the message
     */
    private <T> void load(Class<T> clazz) throws Exception {
        if (clazz.getAnnotation(AmqpController.class) == null) {
            throw new IllegalAccessException("Not annotated class");
        }
        // prepare proxy
        ProxyFactory factory = new ProxyFactory();
        factory.setSuperclass(clazz);
        factory.setFilter(method -> method.isAnnotationPresent(YouShallNotPass.class));

        // instantiate clazz proxified
        Object bean = factory.create(new Class<?>[0], new Object[0], this::authorization);
        Channel channel = conn.createChannel();

        // search handler and create consumers
        Stream.of(clazz.getDeclaredMethods())
                .filter(method1 -> method1.isAnnotationPresent(Handler.class))
                .collect(Collectors.toMap(method1 -> method1.getAnnotation(Handler.class).queue(), Function.identity()))
                .forEach((queue, method) -> {
            try {
                // create consumer
                channel.basicConsume(queue, (ConsumerAdapter) (consumerTag, envelope, properties, body) -> {
                    try {
                        // here we process "authentication" part, now we know who send the message
                        String user = properties.getHeaders().getOrDefault("auth", "anonymous").toString();
                        ThreadContext.put("user", user);
                        Boolean ack = (Boolean) method.invoke(bean, new String(body));
                        channel.basicAck(envelope.getDeliveryTag(), ack);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        log.error("error", e);
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    }
                });
            } catch (IOException e) {
                log.error("error", e);
            }
        });
    }

    /**
     * @see javassist.util.proxy.MethodHandler#invoke(Object, Method, Method, Object[])
     */
    private Object authorization(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        // here we verify if author of message is allowed to use this method
        String authorizedUser = thisMethod.getAnnotation(YouShallNotPass.class).unlessYouAre();
        if (authorizedUser.equals(ThreadContext.get("user"))) {
            return proceed.invoke(self, args);
        } else {
            log.warn("Trying illegal access to {}", thisMethod.getName());
            return true;
        }
    }
}
