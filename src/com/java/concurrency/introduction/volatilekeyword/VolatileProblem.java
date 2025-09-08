package com.java.concurrency.introduction.volatilekeyword;

public class VolatileProblem {

    private static volatile int value = 0;
    private static volatile boolean ready = false;

    private static class Worker implements Runnable {
        @Override
        public void run() {
            while (!ready) {
                Thread.yield();
            }

            if (value != 1000) {
                throw new IllegalStateException("Inconsistent value detected!");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        while (true) {

            Thread t0 = new Thread(new Worker());
            Thread t1 = new Thread(new Worker());
            Thread t2 = new Thread(new Worker());

            t0.start();
            t1.start();
            t2.start();

            value = 1000;
            ready = true;

            while (t0.getState() != Thread.State.TERMINATED || t1.getState() != Thread.State.TERMINATED || t2.getState() != Thread.State.TERMINATED ) {

            }

            value = 0;
            ready = false;
        }
    }
}