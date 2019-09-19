package com.viseo.fes.java.amqp;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.viseo.fes.java.RabbitHandler;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RabbitManager {
    private Connection conn;

    public RabbitManager(String uri) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException {
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setUri("amqp://userName:password@hostName:portNumber/virtualHost");
//        conn = factory.newConnection();
    }

    public <T> T load(Class<T> clazz) throws IllegalAccessException {
        AmqpController annotation = clazz.getAnnotation(AmqpController.class);
        if (annotation == null) {
            throw new IllegalAccessException("Not annotated class");
        }
        Set<Method> interception = Stream.of(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(Handler.class))
//                .map(method -> method.getAnnotation(Handler.class))
                .collect(Collectors.toSet());
        return (T) Proxy.newProxyInstance(RabbitHandler.class.getClassLoader(), new Class<?>[]{clazz}, (proxy, method, args) -> {
//            if (!interception.contains(method)) {
//
//            }
            return null;
        });
    }
}
