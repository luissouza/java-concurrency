package com.java.concurrency.problems.deadlock.problem;

import com.java.concurrency.problems.deadlock.Account;

class BankTransferWithReentrantLock {
    public static void transfer(Account from, Account to, int amount) {
        from.getLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " locked " + from.getName());

            try { Thread.sleep(100); } catch (InterruptedException e) {}

            to.getLock().lock();
            try {
                System.out.println(Thread.currentThread().getName() + " locked " + to.getName());

                from.withdraw(amount);
                to.deposit(amount);

                System.out.println(Thread.currentThread().getName() + " transferred " + amount +
                        " from " + from.getName() + " to " + to.getName());
            } finally {
                to.getLock().unlock();
            }

        } finally {
            from.getLock().unlock();
        }
    }
}

public class DeadLockProblemWithReentrantLock {
    public static void main(String[] args) {
        Account accountA = new Account("AccountA", 1000);
        Account accountB = new Account("AccountB", 2000);

        // Thread 1: transfer from AccountA to AccountB
        Thread t1 = new Thread(() -> {
            BankTransferWithReentrantLock.transfer(accountA, accountB, 100);
        }, "Thread-1");

        // Thread 2: transfer from AccountB to AccountA
        Thread t2 = new Thread(() -> {
            BankTransferWithReentrantLock.transfer(accountB, accountA, 200);
        }, "Thread-2");

        t1.start();
        t2.start();
    }
}
