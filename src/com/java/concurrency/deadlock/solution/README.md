# Deadlock Solutions in Java

This code contains two approaches to fixing **deadlocks**:

1. Using `synchronized` with **lock ordering**
2. Using `ReentrantLock` with **`tryLock()` + timeout + retry**

Both approaches ensure that threads can safely transfer money between accounts **without the risk of deadlock**.

---

## Solution 1: Fix with `synchronized`

### What Was Done
We introduced a **global lock ordering rule** to guarantee that all threads acquire locks in the same order, regardless of transfer direction.

Instead of locking `from` then `to` directly, we:

1. Compare the account names (or IDs).
2. Decide which lock should always be acquired first (`firstLock`).
3. Acquire locks in that fixed order.

### Fixed Code (Excerpt)

```java
public static void transfer(Account from, Account to, int amount) {
    Account firstLock, secondLock;

    // Define lock order deterministically (e.g., by account name)
    if (from.getName().compareTo(to.getName()) < 0) {
        firstLock = from;
        secondLock = to;
    } else {
        firstLock = to;
        secondLock = from;
    }

    synchronized (firstLock) {
        System.out.println(Thread.currentThread().getName() + " locked " + firstLock.getName());

        synchronized (secondLock) {
            System.out.println(Thread.currentThread().getName() + " locked " + secondLock.getName());

            from.withdraw(amount);
            to.deposit(amount);

            System.out.println(Thread.currentThread().getName() +
                    " transferred " + amount + " from " + from.getName() +
                    " to " + to.getName());
        }
    }
}
```

### Why This Fix Works
- Threads no longer compete in opposite directions.
- Every transfer acquires locks in the **same global order**.
- Because circular waiting requires inconsistent lock order, this condition is eliminated.

**Result:** Deadlock is impossible.

---

## Solution 2: Fix with `ReentrantLock` + `tryLock()`

### What Was Done
The original version used `lock()`, which blocks indefinitely and can easily cause deadlock.  
We replaced it with `tryLock(timeout, TimeUnit)` and added a **retry loop with backoff**.

1. Try to acquire the first lock within a timeout.
2. If successful, try to acquire the second lock within a timeout.
3. If both locks are acquired → perform the transfer.
4. If not, release any acquired locks, back off briefly, and retry.

### Fixed Code (Excerpt)

```java
public static void transfer(Account from, Account to, int amount) {
    while (true) {
        try {
            // Attempt to acquire "from" account lock
            if (from.getLock().tryLock(100, TimeUnit.MILLISECONDS)) {
                try {
                    System.out.println(Thread.currentThread().getName() + " locked " + from.getName());

                    // Attempt to acquire "to" account lock
                    if (to.getLock().tryLock(100, TimeUnit.MILLISECONDS)) {
                        try {
                            System.out.println(Thread.currentThread().getName() + " locked " + to.getName());

                            from.withdraw(amount);
                            to.deposit(amount);

                            System.out.println(Thread.currentThread().getName() +
                                    " transferred " + amount + " from " + from.getName() +
                                    " to " + to.getName());

                            break;
                        } finally {
                            to.getLock().unlock();
                        }
                    }
                } finally {
                    from.getLock().unlock();
                }
            }

            // Backoff before retrying to avoid busy spinning
            Thread.sleep(50);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

### Why This Fix Works
- `tryLock(timeout)` prevents indefinite blocking.
- If a thread cannot acquire both locks, it will **give up, release any acquired lock, wait briefly, and retry**.
- This ensures that eventually one thread succeeds, and progress continues.
- Circular waiting is broken because no thread waits forever.

---