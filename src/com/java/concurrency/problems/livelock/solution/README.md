# LiveLock Solution

## Overview

This README explains the solution to the LiveLock issue. 
The livelock occurred because two threads attempted to acquire two shared resources in opposite order, leading to continuous retries without progress.
---

## Solution Approach

The solution is simple yet effective: **both threads acquire the shared resources in the same order**. By enforcing a consistent lock acquisition order, we eliminate the circular contention that caused the livelock.

### Details

1. **Choose a resource order**
    - For example, always acquire `resourceA` first, then `resourceB`.
    - Ensure Thread-1 and Thread-2 both acquire `resourceA` first, then `resourceB`.

### Example Behavior

- Thread-1 acquires `resourceA` → Thread-2 waits for `resourceA`.
- Thread-1 then acquires `resourceB` → performs the task.
- Thread-1 releases `resourceA` and `resourceB`.
- Thread-2 can now acquire both locks and perform its task.

With this ordering, **threads no longer continuously release and retry**, effectively eliminating livelock.

---

## Output without LiveLock
```java
Thread-1: locked resourceA
Thread-1: performing task with both resources...
Thread-1: released resourceA and resourceB
Thread-2: locked resourceB
Thread-2: locked resourceA
Thread-2: performing task with both resources...
Thread-2: released resourceB and resourceA
Thread-2: locked resourceB
Thread-2: locked resourceA
Thread-2: performing task with both resources...
Thread-2: released resourceB and resourceA
```