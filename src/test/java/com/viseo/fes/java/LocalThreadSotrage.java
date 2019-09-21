package com.viseo.fes.java;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Present local thread storage
 */
@Disabled
class LocalThreadSotrage {
    private static Logger log = LoggerFactory.getLogger(LocalThreadSotrage.class);
    private static class Foo {
        private static ThreadLocal<Integer> threadLocalValue = new ThreadLocal<>();

        int value() {
            return threadLocalValue.get();
        }

        void value(int v) {
            threadLocalValue.set(v);
        }
    }

    @Test
    void foo() throws ExecutionException, InterruptedException {
        new Thread(() -> {
            Foo f1 = new Foo();
            f1.value(12);
            while (true) {
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("plouf {}", f1.value());
            }
        }).start();
        new Thread(() -> {
            Foo f1 = new Foo();
            f1.value(42);
            while (true) {
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("plaf {}", f1.value());
            }
        }).start();
        new CompletableFuture<>().get();
    }
}
