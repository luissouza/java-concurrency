# Thread Starvation Example in Java

## Overview

This second example demonstrates a practical example of **thread starvation** in Java.

In this example, starvation occurs for a different reason. The TaskHandler class contains a synchronized method called executeTask(). 

This method first performs a mathematical operation, and then it enters an infinite loop repeatedly printing a message such as:

## Code Example

```java
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
```

The main program then creates 10 threads, all of which attempt to call executeTask() on the same shared instance of TaskHandler.

According to the intended logic, each thread should perform its own computation. But when you run the program, you’ll notice that only one thread (e.g., Thread-1) actually executes. This thread monopolizes the lock and prevents the others from ever running the method.


## Output with Starvation

Based on the output, we can observe that the high-priority threads are executed far more frequently than the low-priority thread:

```java
Thread-1 keeps running and holding the lock...
Thread-1 keeps running and holding the lock...
Thread-1 keeps running and holding the lock...
Thread-1 keeps running and holding the lock...
Thread-1 keeps running and holding the lock...
Thread-1 keeps running and holding the lock...
Thread-1 keeps running and holding the lock...
Thread-1 keeps running and holding the lock...
Thread-1 keeps running and holding the lock...
```