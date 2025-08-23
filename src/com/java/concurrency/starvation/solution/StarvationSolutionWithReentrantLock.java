package com.java.concurrency.starvation.solution;

import java.util.concurrent.locks.ReentrantLock;

public class StarvationSolutionWithReentrantLock {
    public static void main(String[] args) {

        ReentrantLock lock = new ReentrantLock(true);

        Thread lowPriorityThread = new Thread(() -> {
            while (true) {
                lock.lock();
                try {
                    System.out.println("Low priority thread running...");
                } finally {
                    lock.unlock();
                }
            }
        });

        Thread highPriorityThread = new Thread(() -> {
            while (true) {
                lock.lock();
                try {
                    System.out.println("High priority thread running...");
                } finally {
                    lock.unlock();
                }
            }
        });

        lowPriorityThread.setPriority(Thread.MIN_PRIORITY);
        highPriorityThread.setPriority(Thread.MAX_PRIORITY);

        lowPriorityThread.start();
        highPriorityThread.start();
    }
}

