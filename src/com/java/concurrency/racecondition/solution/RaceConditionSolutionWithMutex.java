package com.java.concurrency.racecondition.solution;

class CounterWithMutex {

    private int value = 0;
    private final Object mutex = new Object();

    public void increment() {
        synchronized (mutex) {
            value++;
        }
    }

    public int getValue() {
        synchronized (mutex) {
            return value;
        }
    }
}

public class RaceConditionSolutionWithMutex {

    public static void main(String[] args) throws InterruptedException {

        CounterWithMutex counter = new CounterWithMutex();

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
