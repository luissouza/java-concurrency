package com.java.concurrency.introduction.volatilekeyword;

public class SimpleThreadWithOutSynchronized {

    private static int counter = 0;

    public static void main(String[] args) {

        // Creating multiple threads using a loop
        Task task = new Task();
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(task, "Thread-" + i);
            thread.start();
        }
    }

    // Inner class implementing Runnable
    static class Task implements Runnable {
        @Override
        public void run() {
            int value = ++counter; // pre-increment
            String name = Thread.currentThread().getName();
            System.out.printf("Running %s => counter: %d%n", name, value);
        }
    }
}
