# DeadLock Problems in Java

This code demonstrates **deadlock problems** in Java when using `synchronized` and `ReentrantLock`.

---

## Deadlock with `synchronized`

### Problem
In the first example (`DeadlockBankTransferWithSynchronized`), two threads perform opposite money transfers:

- `Thread-1`: transfers from **AccountA → AccountB**
- `Thread-2`: transfers from **AccountB → AccountA**

Each thread acquires locks in a **different order**:

1. `Thread-1` locks **AccountA**, then waits for **AccountB**.
2. `Thread-2` locks **AccountB**, then waits for **AccountA**.

Both threads are now stuck **waiting on each other** → this is the classic deadlock scenario.

### Root Cause
- Locks are acquired in **inconsistent order** across different threads.
- Once each thread holds one lock and requests the other, **circular waiting** occurs.

---

## Deadlock with `ReentrantLock`

### Problem
In the second example (`DeadLockProblemWithReentrantLock`), the same transfer scenario happens, but using `ReentrantLock`.

- `Thread-1` locks **AccountA**, then tries to lock **AccountB**.
- `Thread-2` locks **AccountB**, then tries to lock **AccountA**.

Because both use `lock()` (which blocks indefinitely), the system reaches the same **deadlock condition**.

### Root Cause
- `lock()` does not time out → threads can wait forever.
- Just like with `synchronized`, inconsistent lock ordering leads to circular waiting.

---