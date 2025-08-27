# LiveLockProblem Example

## Overview

This Java program demonstrates a scenario where **two threads repeatedly attempt to acquire two shared locks** (`resourceA` and `resourceB`) in opposite order. Although the program uses **`tryLock`** with timeouts to avoid DeadLocks, it can still result in a **LiveLock**.

---

## What is a Livelock?

A **LiveLock** occurs when:

- Threads are **actively running**, not blocked.
- They **cannot make progress** because they keep responding to each other's actions.
- CPU cycles are wasted on retries instead of performing meaningful work.

Unlike a **DeadLock**, where threads are stuck and waiting indefinitely, in a LiveLock:

- Threads are **not blocked** but continuously retry operations.
- The system appears active but is effectively stalled.

---

## Why This Code Generates Livelock

### Code Behavior

1. **Thread-1** tries to lock `resourceA` first, then `resourceB`.
2. **Thread-2** tries to lock `resourceB` first, then `resourceA`.
3. Both threads use **`tryLock` with timeouts**:
    - If a lock cannot be acquired, the thread **releases any previously acquired locks** and retries.

### Scenario Leading to Livelock

1. Thread-1 acquires `resourceA`, and Thread-2 acquires `resourceB` at roughly the same time.
2. Both threads try to acquire the second resource:
    - Thread-1 tries `resourceB` → fails.
    - Thread-2 tries `resourceA` → fails.
3. Both threads release their first lock and retry.
4. On retry, timing may repeat the same pattern:
    - Thread-1 takes `resourceA`, Thread-2 takes `resourceB`.
    - They fail again and release locks.
5. This cycle continues indefinitely, consuming CPU but never completing the task.

---

## Causes of Livelock

1. **Opposite lock acquisition order**  
   Threads try to acquire resources in reverse order, creating contention.

2. **Immediate retry after failure**  
   Threads immediately retry after releasing the first lock, often colliding again.

3. **Timeout-based `tryLock`**  
   While preventing deadlock, it **does not prevent livelock**, because threads can continuously respond to each other.

---

## Output with LiveLock
```java
Thread-1: locked resourceA
Thread-2: locked resourceB
Thread-2: couldn't lock resourceA, unlocking resourceB...
Thread-2: locked resourceB
Thread-1: couldn't lock resourceB, unlocking resourceA...
Thread-1: locked resourceA
Thread-1: couldn't lock resourceB, unlocking resourceA...
Thread-1: locked resourceA
Thread-2: couldn't lock resourceA, unlocking resourceB...
Thread-2: locked resourceB
Thread-1: couldn't lock resourceB, unlocking resourceA...
```

## Observations

- Livelocks are **harder to detect** than deadlocks because threads appear active.
- The `System.out.println` statements demonstrate the livelock behavior in logs, showing repeated retries.

