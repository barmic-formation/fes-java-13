package com.viseo.fes.java;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;

class ThirdPart {
    private static Logger log = LoggerFactory.getLogger(ThirdPart.class);

    @FunctionalInterface
    interface Toto {
        Integer get();
    }

    @Test
    void run() {
        Class<?>[] classes = new Class<?>[]{Toto.class}; // <- seul des interfaces peut être utilisée
        Toto t = () -> 45;
        ClassLoader cLoader = Thread.currentThread().getContextClassLoader();

        Toto myAwesomeProxy = (Toto) Proxy.newProxyInstance(cLoader, classes, (proxy, method, args) -> {
            log.info("handled");
            return t.get();
        });

        log.info("call myAwesomeProxy {}", myAwesomeProxy.get());
    }
}
