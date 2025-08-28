
package com.java.concurrency.problems.deadlock.solution;

import com.java.concurrency.problems.deadlock.Account;

class BankTransferWithSynchronizedSolution {
    public static void transfer(Account from, Account to, int amount) {
        Account firstLock, secondLock;

        if (from.getName().compareTo(to.getName()) < 0) {
            firstLock = from;
            secondLock = to;
        } else {
            firstLock = to;
            secondLock = from;
        }

        synchronized (firstLock) {
            System.out.println("FROM::: " + Thread.currentThread().getName() + " locked " + firstLock.getName());

            try { Thread.sleep(100); } catch (InterruptedException e) {}

            synchronized (secondLock) {
                System.out.println("TO::: " + Thread.currentThread().getName() + " locked " + secondLock.getName());

                from.withdraw(amount);
                to.deposit(amount);

                System.out.println(Thread.currentThread().getName() +
                        " transferred " + amount + " from " + from.getName() +
                        " to " + to.getName());
            }
        }
    }
}

public class DeadlockSolutionWithSynchronized {
    public static void main(String[] args) {

        Account accountA = new Account("AccountA", 1000);
        Account accountB = new Account("AccountB", 2000);

        // Thread 1: transfer from AccountA to AccountB
        Thread t1 = new Thread(() -> {
            BankTransferWithSynchronizedSolution.transfer(accountA, accountB, 100);
        }, "Thread-1");

        // Thread 2: transfer from AccountB to AccountA
        Thread t2 = new Thread(() -> {
            BankTransferWithSynchronizedSolution.transfer(accountB, accountA, 200);
        }, "Thread-2");

        t1.start();
        t2.start();
    }
}
