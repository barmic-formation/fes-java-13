package com.viseo.fes.java;

import com.viseo.fes.java.amqp.AmqpController;
import com.viseo.fes.java.amqp.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AmqpController
public class RabbitHandler {
    private static Logger log = LoggerFactory.getLogger(RabbitHandler.class);
    @Handler(queue = "viseo")
    public Boolean plouf(String payload) {
        log.info(payload);
        return true;
    }
}
