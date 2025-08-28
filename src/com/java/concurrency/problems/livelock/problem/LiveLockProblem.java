package com.java.concurrency.problems.livelock.problem;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

public class LiveLockProblem {

        // Two locks shared by both threads
        private static final Lock resourceA = new ReentrantLock();
        private static final Lock resourceB = new ReentrantLock();

        public static void main(String[] args) {

            // First worker thread (tries resourceA first, then resourceB)
            Thread t1 = new Thread(() -> {
                while (true) {
                    try {
                        if (!resourceA.tryLock(500, TimeUnit.MILLISECONDS)) {
                            System.out.println(Thread.currentThread().getName() + ": couldn't lock resourceA, retrying...");
                            continue;
                        }
                        System.out.println(Thread.currentThread().getName() + ": locked resourceA");

                        if (!resourceB.tryLock(200, TimeUnit.MILLISECONDS)) {
                            System.out.println(Thread.currentThread().getName() + ": couldn't lock resourceB, unlocking resourceA...");
                            resourceA.unlock();
                            continue;
                        }
                        System.out.println(Thread.currentThread().getName() + "T: locked resourceB");

                        // Simulated work
                        System.out.println(Thread.currentThread().getName() + ": performing task with both resources...");

                        // Release locks
                        resourceB.unlock();
                        resourceA.unlock();
                        System.out.println(Thread.currentThread().getName() +  ": released resourceA and resourceB");

                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }, "Thread-1");

            // Second worker thread (tries resourceB first, then resourceA)
            Thread t2 = new Thread(() -> {
                while (true) {
                    try {
                        if (!resourceB.tryLock(500, TimeUnit.MILLISECONDS)) {
                            System.out.println(Thread.currentThread().getName() +  ": couldn't lock resourceB, retrying...");
                            continue;
                        }
                        System.out.println(Thread.currentThread().getName() +  ": locked resourceB");

                        if (!resourceA.tryLock(200, TimeUnit.MILLISECONDS)) {
                            System.out.println(Thread.currentThread().getName() +  ": couldn't lock resourceA, unlocking resourceB...");
                            resourceB.unlock();
                            continue;
                        }
                        System.out.println(Thread.currentThread().getName() +  ": locked resourceA");

                        // Simulated work
                        System.out.println(Thread.currentThread().getName() +  ": performing task with both resources...");

                        // Release locks
                        resourceA.unlock();
                        resourceB.unlock();
                        System.out.println(Thread.currentThread().getName() +  ": released resourceB and resourceA");

                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }, "Thread-2");

            // Start both threads
            t1.start();
            t2.start();
        }
    }
