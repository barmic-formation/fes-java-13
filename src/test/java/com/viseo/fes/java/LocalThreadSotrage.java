package com.viseo.fes.java;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Present local thread storage
 */
class LocalThreadSotrage {
    private static Logger log = LoggerFactory.getLogger(LocalThreadSotrage.class);
    private static class Foo {
        private static ThreadLocal<String> threadLocalValue = new ThreadLocal<>();
        private static String local;

        Foo(String value) {
            threadLocalValue.set(value);
            local = value;
        }

        @Override
        public String toString() {
            return "Foo{thread local:" + threadLocalValue.get() + "/local:" + local + '}';
        }
    }

    @Test
    void foo() throws InterruptedException {
        List<Thread> threads = Stream.of(
                new Thread(buildRunnable("plouf")),
                new Thread(buildRunnable("plaf"))
        )
                .peek(Thread::start)
                .collect(Collectors.toList());
        Thread.sleep(30_00);
        threads.forEach(Thread::interrupt);
    }

    private Runnable buildRunnable(String name) {
        return () -> {
            Foo f1 = new Foo(name);
            try {
                while (true) {
                    Thread.sleep(500);
                    log.info("{}", f1);
                }
            } catch (InterruptedException e) {
                log.error("Interrupted");
            }
        };
    }
}
