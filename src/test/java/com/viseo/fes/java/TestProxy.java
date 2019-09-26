package com.viseo.fes.java;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;

class TestProxy {
    private static Logger log = LoggerFactory.getLogger(TestProxy.class);

    @FunctionalInterface
    interface Toto {
        Integer get();
    }

    @Test
    void foo() {
        Class<?>[] classes = new Class<?>[]{Toto.class};
        Toto t = () -> 45;
        ClassLoader cLoader = Thread.currentThread().getContextClassLoader();

        Toto coucou = (Toto) Proxy.newProxyInstance(cLoader, classes, (proxy, method, args) -> {
            log.info("handled");
            return t.get();
        });

        log.info("usage {}", coucou.get());
    }
}
