package com.java.concurrency.racecondition.problem;

class Counter {

    private int value = 0;

    public void increment() {
        value++;
    }

    public int getValue() {
        return value;
    }
}

public class RaceConditionProblem {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

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


        // Create and tell to JVM to schedulle the execution of the run() method in thread
        t1.start();
        t2.start();

        // Tell to main thread to wait for the execution of this two trheads T1 and T2
        t1.join();
        t2.join();

        System.out.println("Expected value: 20000");
        System.out.println("Actual value: " + counter.getValue());
    }
}