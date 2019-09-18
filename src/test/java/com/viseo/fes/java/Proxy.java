package com.viseo.fes.java;

import com.google.common.flogger.FluentLogger;
import org.junit.jupiter.api.Test;

import java.util.List;

class Proxy {
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();
    @FunctionalInterface
    interface Toto {
        Integer get();
    }
    @Test
    void foo() {
        List<Class<Toto>> classes = List.of(Toto.class);
        Toto t = () -> 45;
        Toto coucou = (Toto)java.lang.reflect.Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), classes.toArray(new Class<?>[0]), (proxy, method, args) -> {
            logger.atInfo().log("coucou");
            return t.get();
        });
        logger.atInfo().log("coucou %d", coucou.get());
    }
}
