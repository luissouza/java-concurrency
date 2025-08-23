# Starvation Solution Example in Java

## Overview

This code demonstrates a solution using **ReentrantLock** to avoid **starvation** issues in Java. 
To achieve this, it is necessary to enable the fair locking, which ensures that threads acquire the lock in a first-come, first-served (FIFO) order.

## Solution Using: `ReentrantLock`

```java
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
```
## Conclusion

The use of new ReentrantLock(true) ensures fairness (justice in the waiting queue). This prevents starvation, since no thread (even a low-priority one) will wait forever — all of them will execute in order of arrival.





