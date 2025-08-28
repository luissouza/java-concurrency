package com.java.concurrency.problems.racecondition.solution;

class CounterWithSynchronized {

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
        CounterWithSynchronized counter = new CounterWithSynchronized();

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