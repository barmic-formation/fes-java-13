package com.viseo.fes.java.amqp;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RabbitManager {
    private static Logger log = LoggerFactory.getLogger(RabbitManager.class);
    private final Connection conn;

    public RabbitManager(String uri) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(uri);
        conn = factory.newConnection();
    }

    public void start() throws ExecutionException, InterruptedException {
        new CompletableFuture<>().get();
    }

    public <T> void load(Class<T> clazz) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException, IOException {
        AmqpController annotation = clazz.getAnnotation(AmqpController.class);
        if (annotation == null) {
            throw new IllegalAccessException("Not annotated class");
        }
        ProxyFactory factory = new ProxyFactory();
        factory.setSuperclass(clazz);
        factory.setFilter(method -> method.isAnnotationPresent(Handler.class));

        Map<String, Method> handlers = Stream.of(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(Handler.class))
                .collect(Collectors.toMap(method -> method.getAnnotation(Handler.class).queue(), Function.identity()));

        MethodHandler handler = (self, thisMethod, proceed, args) -> {
            log.info("hello");
            return proceed.invoke(self, args);
        };

        Object bean = factory.create(new Class<?>[0], new Object[0], handler);
        Channel channel = conn.createChannel();

        handlers.forEach((queue, method) -> {
            try {
                channel.basicConsume(queue, (ConsumerAdapter) (consumerTag, envelope, properties, body) -> {
                    try {
                        Boolean ack = (Boolean) method.invoke(bean, new String(body));
                        channel.basicAck(envelope.getDeliveryTag(), ack);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        log.error("error", e);
                    }
                });
            } catch (IOException e) {
                log.error("error", e);
            }
        });
    }
}
