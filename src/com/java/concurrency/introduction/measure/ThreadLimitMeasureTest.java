package com.java.concurrency.introduction.measure;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

public class ThreadLimitMeasureTest {
    public static void main(String[] args) {
        AtomicInteger createdThreads = new AtomicInteger();

        try {
            for (;;) {
                Thread t = new Thread(() -> {
                    createdThreads.incrementAndGet();
                    LockSupport.park();
                });
                t.start();
            }
        } catch (OutOfMemoryError oom) {
            System.out.println("Max thread num: " + createdThreads.get());
            oom.printStackTrace();
        }
    }
}
