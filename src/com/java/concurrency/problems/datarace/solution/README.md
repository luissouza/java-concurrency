# Solving Data Races in Java with AtomicInteger

## The Solution: AtomicInteger

Java provides the **`AtomicInteger`** class in the `java.util.concurrent.atomic` package.  

It offers methods like `incrementAndGet()` that are:
- **Atomic** → cannot be interrupted by other threads.
- **Thread-safe** → ensures correctness without explicit synchronization.
- **Efficient** → uses low-level CPU instructions (CAS: Compare-And-Swap) instead of locks.

---

## Example Code
```java
import java.util.concurrent.atomic.AtomicInteger;

public class DataRaceFixedAtomic {
    private static AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.incrementAndGet(); // atomic increment
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.incrementAndGet(); // atomic increment
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
        
        System.out.println("Final counter value: " + counter.get());
    }
}
```

## Output with AtomicInteger
```java
Final counter value: 2000
```