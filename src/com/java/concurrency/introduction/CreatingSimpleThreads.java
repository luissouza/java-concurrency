package com.java.concurrency.introduction;


public class CreatingSimpleThreads {

    // 1. Using Runnable
    static class MyThreadWithRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("Class MyThread implementing Runnable: " + Thread.currentThread().getName());
        }
    }

    // 2. Extending Thread
    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("Class MyThread extending Thread: " + Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) {

        System.out.println("Current Thread: " + Thread.currentThread().getName());

        // Create thread with Runnable
        Thread t1 = new Thread(new MyThreadWithRunnable());

        // Create thread by extending Thread
        MyThread t2 = new MyThread();

        // Start both threads
        t1.start();
        t2.start();

    }
}
