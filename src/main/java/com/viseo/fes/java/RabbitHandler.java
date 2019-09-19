package com.viseo.fes.java;

import com.viseo.fes.java.amqp.AmqpController;
import com.viseo.fes.java.amqp.Handler;

@AmqpController
public class RabbitHandler {
    @Handler(queue = "")
    public Boolean plouf(Object payload) {
        return true;
    }
}
