# Race Condition Problem in Java

## Overview
This code demonstrates a simple **race condition** in Java.  
A race condition occurs when multiple threads access and modify a shared resource concurrently without proper synchronization, leading to unpredictable results.

## Code Explanation
The program defines a `Counter` class with a `value` field and an `increment()` method:

```java
class Counter {
    private int value = 0;

    public void increment() {
        value++; // non-atomic operation
    }

    public int getValue() {
        return value;
    }
}
```

Two threads are created, each incrementing the counter 10000 times:

```java
Thread t1 = new Thread(() -> {
    for (int i = 0; i < 10000; i++) {
        counter.increment();
    }
});

Thread t2 = new Thread(() -> {
    for (int i = 0; i < 10000; i++) {
        counter.increment();
    }
});
```

Create and tell to JVM to schedulle the execution of the run() method in thread using `start()`:

```java
t1.start();
t2.start();
```

Tell to main thread to wait for the execution of this two trheads T1 and T2 using `join()`:

```java
t1.join();
t2.join();
```

Finally, the result is printed:

```java
System.out.println("Expected value: 20000");
System.out.println("Actual value: " + counter.getValue());
```

## The Problem
- The operation `value++` is **not atomic**.  
  It is actually composed of three steps:
  1. Read the current value.
  2. Add 1.
  3. Write the new value back.

- If two threads execute these steps at the same time, one increment can overwrite the other, causing **lost updates**.

- As a result, the final expected value as **less than 20000**.
- And the real result may be **less than 20000**, depending on thread scheduling.

## Why It May Not Always Fail
On fast processors (like Intel i9) or in some JVM executions, you might always see `20000`.  
This is because the threads might run sequentially by chance, hiding the race condition.

---
