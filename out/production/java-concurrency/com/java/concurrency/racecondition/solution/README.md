# Race Condition Example in Java - Fixed Version

## Overview

This code demonstrates how to **fix a race condition** in Java. The original code suffered from a race condition because multiple threads incremented a shared integer without synchronization. Here we provide two corrected versions:

1. Using the `synchronized` keyword.
2. Using `AtomicInteger`.

Both approaches ensure thread-safe increments and guarantee the correct final result.

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

## Pros and Cons

### `synchronized`

**Pros:**

* Easy to understand and apply.
* Works with any operation, not just primitives.
* Protects complex critical sections.

**Cons:**

* Performance overhead due to blocking.
* Can reduce scalability under contention.
* Risk of deadlocks if used incorrectly.

### `AtomicInteger`

**Pros:**

* Lock-free and non-blocking.
* Scales better under high contention.
* Ideal for simple atomic operations like increment.

**Cons:**

* Limited to simple operations on primitives (`int`, `long`).
* Not suitable for complex critical sections involving multiple variables.
* Can reduce code readability if multiple atomic operations are combined.

---

## Conclusion

* Use **`AtomicInteger`** for high-performance counters or simple atomic updates.
* Use **`synchronized`** for protecting larger critical sections or multiple related variables.

Both approaches fix the race condition and ensure the correct final result.
