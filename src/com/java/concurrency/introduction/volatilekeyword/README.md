# Java Multithreading - Volatile

This guide explains the execution flow of a Java program demonstrating a common concurrency issue and how the `volatile` keyword resolves it.

---

## 1. Creating Threads

The first step is to create three threads:

```java
Thread t0 = new Thread(new Worker());
Thread t1 = new Thread(new Worker());
Thread t2 = new Thread(new Worker());
```

---

## 2. Starting Threads

Next, the threads are started:

```java
t0.start();
t1.start();
t2.start();
```

---

## 3. Setting Shared Variables

We then assign values to the shared variables `value` and `ready`:

```java
value = 1000;
ready = true;
```

---

## 4. Waiting for Threads to Finish

The following code ensures the application does not proceed until threads `t0`, `t1`, and `t2` have actually finished execution:

```java
while (t0.getState() != Thread.State.TERMINATED
       || t1.getState() != Thread.State.TERMINATED
       || t2.getState() != Thread.State.TERMINATED) {
    // busy wait
}
```

---

## 5. Resetting Shared Variables

After the threads finish, the shared variables are reset:

```java
value = 0;
ready = false;
```

---

## How It Works

When we call `start()`, the OS is instructed to execute the `run()` method of each thread:

```java
private static class Worker implements Runnable {
    @Override
    public void run() {
        while (!ready) {
            Thread.yield();
        }

        if (value != 1000) {
            throw new IllegalStateException("Inconsistent value detected!");
        }
    }
}
```

* The **execution order** of `t0`, `t1`, and `t2` is determined by the **operating system**, not Java.
* Inside `Worker`, the thread loops until `ready` becomes `true`.
* `Thread.yield()` is a hint to the processor:

  > "I have no work to do right now, you may let another thread run."
* Once `ready` is `true`, the check `if (value != 1000)` executes.

---

## Can the Exception Actually Happen?

Logically, one might think this exception should never be thrown because:

```java
value = 1000;
ready = true;
```

Since `value` is set before `ready`, any thread seeing `ready == true` should also see `value == 1000`.

However, in practice, exceptions can occur:

```
Exception in thread "Thread-60595" java.lang.IllegalStateException: Inconsistent value detected!
    at com.java.concurrency.introduction.volatilekeyword.VolatileProblem$Worker.run(VolatileProblem.java:16)
...
```

---

## The Problem

The root cause lies in **CPU caching**:

* When a thread writes to a variable, it may update a CPU cache rather than main memory immediately.
* Another thread may read the variable from its own cache and see a stale value.

Example:

* Thread A updates `int variable = 1`.
* Thread B reads the variable but sees `0` because it reads from its CPU cache instead of main memory.

---

## The Solution: `volatile`

Using `volatile` ensures all reads and writes occur directly in **main memory**, bypassing the CPU cache:

```java
private static volatile int value = 0;
private static volatile boolean ready = false;
```

This guarantees visibility of changes across threads.

---

## Trade-offs of `volatile`

* **Performance cost:** Accessing main memory is slightly slower than reading from CPU cache.
* **Selective use:** Not all variables should be `volatile`; overusing it would negate CPU cache benefits and reduce overall performance.

---

## Conclusion

This example demonstrates a fundamental concurrency issue in Java: **visibility of shared variables across threads**.

* Without `volatile`, threads may see stale values.
* With `volatile`, memory visibility is guaranteed, preventing subtle and difficult-to-detect bugs.
