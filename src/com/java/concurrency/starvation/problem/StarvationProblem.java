package com.java.concurrency.starvation.problem;

class TaskHandler {

    public synchronized void executeTask() {
        String threadName = Thread.currentThread().getName();

        long sum = 0;
        for (int i = 0; i < 1_000_000; i++) {
            sum += (i * 3) % 7;
        }
        System.out.println("Thread " + threadName + " end the calc with the result: " + sum);

        while(true) {
            System.out.println(">> " + threadName + " keeps running and holding the lock...");
        }
    }
}

public class StarvationProblem {
    public static void main(String[] args) {

        TaskHandler handler = new TaskHandler();

        for (int i = 1; i <= 10; i++) {
            Thread t = new Thread(() -> handler.executeTask(), "Thread-" + i);
            t.start();
        }
    }
}
