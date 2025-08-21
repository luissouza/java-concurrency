package com.java.concurrency.racecondition.solution;

class CounterB {

    private int value = 0;

    public synchronized  void increment() {
        value++;
    }

    public int getValue() {
        return value;
    }
}

public class RaceConditionSolutionWithSynchronized {
    public static void main(String[] args) throws InterruptedException {
        CounterB counter = new CounterB();

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