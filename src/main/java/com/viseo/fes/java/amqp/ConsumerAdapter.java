package com.viseo.fes.java.amqp;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

import java.io.IOException;

@FunctionalInterface
public interface ConsumerAdapter extends Consumer {
    @Override
    default void handleConsumeOk(String consumerTag) {}
    @Override
    default void handleCancelOk(String consumerTag) {}
    @Override
    default void handleCancel(String consumerTag) {}
    @Override
    default void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {}
    @Override
    default void handleRecoverOk(String consumerTag) {}

    @Override
    void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException;
}
