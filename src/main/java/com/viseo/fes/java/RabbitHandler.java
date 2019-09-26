package com.viseo.fes.java;

import com.viseo.fes.java.amqp.AmqpController;
import com.viseo.fes.java.amqp.Handler;
import com.viseo.fes.java.amqp.YouShallNotPass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AmqpController
public class RabbitHandler {
    private static Logger log = LoggerFactory.getLogger(RabbitHandler.class);

    @Handler(queue = "viseo")
    public Boolean plouf(String payload) {
        log.info("not authenticated {}", payload);
        return true;
    }

    @YouShallNotPass(unlessYouAre = "vincent")
    @Handler(queue = "secViseo")
    public Boolean secutiry(String payload) {
        log.info("authenticated {}", payload);
        return true;
    }
}
