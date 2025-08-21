package com.java.concurrency.racecondition.solution;

import java.util.concurrent.atomic.AtomicInteger;

class CounterA {
    private AtomicInteger value = new AtomicInteger(0);

    public void increment() {
        value.incrementAndGet();
    }

    public int getValue() {
        return value.get();
    }
}

public class RaceConditionSolutionWithAtomicInteger {
    public static void main(String[] args) throws InterruptedException {
        CounterA counter = new CounterA();

        // Create two threads incrementing the counter 10000 times each
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                counter.increment();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                counter.increment();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Expected value: 20000");
        System.out.println("Actual value: " + counter.getValue());
    }
}