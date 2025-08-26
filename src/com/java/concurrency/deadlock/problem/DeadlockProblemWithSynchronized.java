package com.java.concurrency.deadlock.problem;

import com.java.concurrency.deadlock.Account;

class BankTransferWithSynchronized {
    public static void transfer(Account from, Account to, int amount) {
        synchronized (from) {
            System.out.println("FROM::: " + Thread.currentThread().getName() + " locked " + from.getName());

            try { Thread.sleep(100); } catch (InterruptedException e) {}

            synchronized (to) {
                System.out.println("TO::: " + Thread.currentThread().getName() + " locked " + to.getName());

                from.withdraw(amount);
                to.deposit(amount);

                System.out.println(Thread.currentThread().getName() +
                        " transferred " + amount + " from " + from.getName() +
                        " to " + to.getName());
            }
        }
    }
}

public class DeadlockProblemWithSynchronized {
    public static void main(String[] args) {

        Account accountA = new Account("AccountA", 1000);
        Account accountB = new Account("AccountB", 2000);

        // Thread 1: transfer from AccountA to AccountB
        Thread t1 = new Thread(() -> {
            BankTransferWithSynchronized.transfer(accountA, accountB, 100);
        }, "Thread-1");

        // Thread 2: transfer from AccountB to AccountA
        Thread t2 = new Thread(() -> {
            BankTransferWithSynchronized.transfer(accountB, accountA, 200);
        }, "Thread-2");

        t1.start();
        t2.start();
    }
}
