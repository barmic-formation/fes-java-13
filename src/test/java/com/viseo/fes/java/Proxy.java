package com.viseo.fes.java;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

class Proxy {
    private static Logger log = LoggerFactory.getLogger(Proxy.class);
    @FunctionalInterface
    interface Toto {
        Integer get();
    }
    @Test
    void foo() {
        List<Class<Toto>> classes = List.of(Toto.class);
        Toto t = () -> 45;
        Toto coucou = (Toto)java.lang.reflect.Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), classes.toArray(new Class<?>[0]), (proxy, method, args) -> {
            log.info("coucou");
            return t.get();
        });
        log.info("coucou {}", coucou.get());
    }
}
