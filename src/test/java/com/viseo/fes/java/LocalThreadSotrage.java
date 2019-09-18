package com.viseo.fes.java;

import com.google.common.flogger.FluentLogger;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Present local thread storage
 */
class LocalThreadSotrage {
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();
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
                logger.atInfo().log("plouf %d", f1.value());
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
                logger.atInfo().log("plaf %d", f1.value());
            }
        }).start();
        new CompletableFuture<>().get();
    }
}
