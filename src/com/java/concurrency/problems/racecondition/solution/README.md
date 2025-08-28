# Race Condition Solution Example in Java

## Overview

This code demonstrates how to **fix a race condition** in Java. The original code suffered from a race condition because multiple threads incremented a shared integer without synchronization. 
Here we provide different corrected versions:

1. Using the `synchronized` keyword.
2. Using `AtomicInteger`.
3. Using a `Mutex` (explicit lock object).
4. Using a `ReentrantLock`.

All approaches ensure thread-safe increments and guarantee the correct final result.

---

## Solution 1: Using `synchronized`

```java
class Counter {
    private int value = 0;

    public synchronized void increment() {
        value++; // atomic because the method is synchronized
    }

    public int getValue() {
        return value;
    }
}
```

* `synchronized` ensures only one thread executes `increment()` at a time.
* Prevents lost updates and guarantees thread-safe increments.

---

## Solution 2: Using `AtomicInteger`

```java
import java.util.concurrent.atomic.AtomicInteger;

class Counter {
    private AtomicInteger value = new AtomicInteger(0);

    public void increment() {
        value.incrementAndGet(); // atomic operation
    }

    public int getValue() {
        return value.get();
    }
}
```

* `AtomicInteger` provides lock-free, thread-safe operations at the hardware level using CAS (Compare-And-Swap).
* Efficient for high-performance counters or simple atomic updates.

---

## Solution 3: Using a `Mutex`

```java
class Counter {
    private int value = 0;
    private final Object mutex = new Object();

    public void increment() {
        synchronized (mutex) {
            value++;
        }
    }

    public int getValue() {
        synchronized (mutex) {
            return value;
        }
    }
}
```

* A `mutex` object is explicitly created and used for locking.
* Equivalent to using `synchronized` on `this`, but makes the lock explicit and separate from the instance itself.

---

## Solution 4: Using `ReentrantLock`

```java
import java.util.concurrent.locks.ReentrantLock;

class Counter {
    private int value = 0;
    private final ReentrantLock lock = new ReentrantLock();

    public void increment() {
        lock.lock();
        try {
            value++;
        } finally {
            lock.unlock();
        }
    }

    public int getValue() {
        lock.lock();
        try {
            return value;
        } finally {
            lock.unlock();
        }
    }
}
```

* `ReentrantLock` provides more flexibility than `synchronized`, such as `tryLock()`, `lockInterruptibly()`, and multiple conditions.
* Must always release the lock in a `finally` block to avoid deadlocks.

---

## Comparative Table: Avoid Race Condition Mechanisms

| Mechanism       | Pros                                                                 | Cons                                                                 |
|-----------------|----------------------------------------------------------------------|----------------------------------------------------------------------|
| `synchronized`  | - Simple and easy to use                                              | - Blocking, may reduce performance under contention                 |
|                 | - Built into the language                                             | - Less flexible (no timeout, no fairness control)                   |
|                 | - Works for complex critical sections                                 |                                                                      |
| `ReentrantLock` | - More flexible (tryLock, lockInterruptibly, fairness policies)       | - More verbose, need to unlock in `finally`                         |
|                 | - Can be re-entered by the same thread                                 | - Slightly more overhead than `synchronized`                        |
|                 | - Supports multiple conditions                                        |                                                                      |
| `Mutex`         | - Explicit lock object, makes intent clear                             | - Equivalent to `synchronized`, so same scalability limits          |
|                 | - Safer when external code might synchronize on the instance           | - Less feature-rich than `ReentrantLock`                             |
| `AtomicInteger` | - Lock-free and highly performant                                     | - Limited to simple atomic operations on primitives                 |
|                 | - Scales well under high contention                                    | - Cannot protect complex critical sections                           |
|                 | - Ideal for counters, flags, simple atomic updates                     | - May reduce readability if multiple atomic operations are combined |




---

## Conclusion

- **`synchronized`**:  Use to protect simple critical sections or multiple related variables. It is easy to use and reliable, but blocking.
- **`ReentrantLock`**: Use when you need more control over synchronization, such as timeouts, fairness policies, or multiple conditions. More flexible, but requires careful lock release.
- **`Mutex`**:         Useful when you want to make locking explicit, avoiding conflicts with external code that might synchronize on the same object. Similar to `synchronized`, but with clearer intent.
- **`AtomicInteger`**: Ideal for counters or simple flags requiring high performance and atomic operations without blocking. Not suitable for complex critical sections involving multiple variables.

All these approaches solve **race condition** problems, but the choice depends on the complexity of concurrent access and the desired performance.
