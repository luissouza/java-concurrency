package com.java.concurrency.problems.datarace.problem;

public class DataRaceProblem {
    private static int counter = 0;

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter++;
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter++;
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final counter value: " + counter);
    }



    public void sum(int a, int b) {

        for(int i =0; i < a; i++) {
            System.out.println(i);
        }

        for(int i =0; i < a; i++) {
            System.out.println(i);
        }
    }
}
