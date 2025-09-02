# Data Race in Java

## What is a Data Race?
A **data race** happens when two or more threads access the same shared variable at the same time, and at least one of them modifies it, **without proper synchronization**.  
This leads to **unpredictable results** because the operations on the variable are not atomic.

---

## Example: Data Race with `counter++`
The following code creates two threads that both increment a shared variable `counter`.  
The expected result is `2000` (each thread increments the counter 1000 times), but due to the data race, the result is often **less than 2000**.

```java
public class DataRaceExample {
    private static int counter = 0;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter++; // not thread-safe → data race
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter++; // not thread-safe → data race
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Expected: 2000
        // Actual: may be inconsistent (e.g., //1985 //1983, //1991, //1888, etc.)
        System.out.println("Final counter value: " + counter);
    }
}
```

## Output with DataRaceProblem
```java
Final counter value: may be inconsistent (e.g., //1985 //1983, //1991, //1888, etc.)
```