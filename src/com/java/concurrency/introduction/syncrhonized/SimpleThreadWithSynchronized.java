package com.java.concurrency.introduction.syncrhonized;

public class SimpleThreadWithSynchronized {

    private static int counter = 0;

    public static void main(String[] args) {

        // Creating multiple threads using a loop
        SimpleThreadWithSynchronized.Task task = new SimpleThreadWithSynchronized.Task();
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(task, "Thread-" + i);
            thread.start();
        }
    }

    // Inner class implementing Runnable
    static class Task implements Runnable {
        @Override
        public synchronized void run() {
            int value = ++counter; // pre-increment
            String name = Thread.currentThread().getName();
            System.out.printf("Running %s => counter: %d%n", name, value);
        }
    }
}
