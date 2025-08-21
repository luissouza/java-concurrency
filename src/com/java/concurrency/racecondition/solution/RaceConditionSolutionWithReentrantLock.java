package com.java.concurrency.racecondition.solution;

import java.util.concurrent.locks.ReentrantLock;

class CounterWithReentrantLock {

    private int value = 0;
    private final ReentrantLock lock = new ReentrantLock();

    public void increment() {
        lock.lock();
        try {
            value++;
        } finally {
            lock.unlock();
        }
    }

    public int getValue() {
        lock.lock();
        try {
            return value;
        } finally {
            lock.unlock();
        }
    }
}


public class RaceConditionSolutionWithReentrantLock {
    public static void main(String[] args) throws InterruptedException {
        CounterWithReentrantLock counter = new CounterWithReentrantLock();

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
