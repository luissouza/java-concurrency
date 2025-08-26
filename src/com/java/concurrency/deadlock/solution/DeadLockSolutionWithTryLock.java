package com.java.concurrency.deadlock.solution;

import com.java.concurrency.deadlock.Account;

import java.util.concurrent.TimeUnit;

class BankTransferWithReentrantLockWithTryLock {
    public static void transfer(Account from, Account to, int amount) {
        while (true) {
            try {

                if (from.getLock().tryLock(100, TimeUnit.MILLISECONDS)) {
                    try {
                        System.out.println(Thread.currentThread().getName() + " locked " + from.getName());

                        if (to.getLock().tryLock(100, TimeUnit.MILLISECONDS)) {
                            try {
                                System.out.println(Thread.currentThread().getName() + " locked " + to.getName());

                                from.withdraw(amount);
                                to.deposit(amount);

                                System.out.println(Thread.currentThread().getName() + " transferred " + amount +
                                        " from " + from.getName() + " to " + to.getName());
                                break;
                            } finally {
                                to.getLock().unlock();
                            }
                        }
                    } finally {
                        from.getLock().unlock();
                    }
                }

                Thread.sleep(50);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class DeadLockSolutionWithTryLock {
    public static void main(String[] args) {
        Account accountA = new Account("AccountA", 1000);
        Account accountB = new Account("AccountB", 2000);

        // Thread 1: transfer from AccountA to AccountB
        Thread t1 = new Thread(() -> {
            BankTransferWithReentrantLockWithTryLock.transfer(accountA, accountB, 100);
        }, "Thread-1");

        // Thread 2: transfer from AccountB to AccountA
        Thread t2 = new Thread(() -> {
            BankTransferWithReentrantLockWithTryLock.transfer(accountB, accountA, 200);
        }, "Thread-2");

        t1.start();
        t2.start();
    }
}
