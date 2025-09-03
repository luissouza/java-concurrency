# Java Multithreading: With and Without synchronized

This code demonstrates the difference between using synchronized and not using it when multiple threads share the same resource.

## It contains two Java programs:

## SimpleThreadWithSynchronized.java → Using the synchronized keyword.

``` java
public class SimpleThreadWithSynchronized {

   private static int counter = 0;

   public static void main(String[] args) {
       SimpleThreadWithSynchronized.Task task = new SimpleThreadWithSynchronized.Task();
       for (int i = 0; i < 5; i++) {
           Thread thread = new Thread(task, "Thread-" + i);
           thread.start();
       }
   }

   static class Task implements Runnable {
       @Override
       public synchronized void run() {
            int value = ++counter;
            String name = Thread.currentThread().getName();
            System.out.printf("Running %s => counter: %d%n", name, value);
      }
   }
}

```
Here, the method run() is marked as synchronized. This means:

Only one thread at a time can execute the run() method on the same Task instance.
It prevents two threads from incrementing the counter at the same time.
The output is consistent and predictable.

## The output may look consistent:

``` java
Running Thread-0 => counter: 1
Running Thread-4 => counter: 2
Running Thread-3 => counter: 3
Running Thread-2 => counter: 4
Running Thread-1 => counter: 5
``` 

## SimpleThreadWithOutSynchronized.java → Does not use synchronized.

``` java
public class SimpleThreadWithOutSynchronized {

    private static int counter = 0;

    public static void main(String[] args) {
        Task task = new Task();
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(task, "Thread-" + i);
            thread.start();
        }
    }

    static class Task implements Runnable {
        @Override
        public void run() {
            int value = ++counter; 
            String name = Thread.currentThread().getName();
            System.out.printf("Running %s => counter: %d%n", name, value);
        }
    }
}
```

Here, run() is not synchronized. This means:

Multiple threads may try to increment the counter at the same time.
The ++counter operation is not atomic (it involves reading, adding, then writing).
As a result, two threads may read the same value before one writes it back → leading to duplicate values or skipped numbers.

## The output may look inconsistent:

``` java
Running Thread-0 => counter: 1
Running Thread-1 => counter: 2
Running Thread-2 => counter: 2  <-- Duplicate value!
Running Thread-3 => counter: 2  <-- Duplicate value!
Running Thread-4 => counter: 5
``` 

## Why is synchronized important here?

## Without synchronized:

Race conditions can occur.
The counter may become corrupted or inconsistent.
The final result depends on the CPU scheduling (which thread runs first), so it changes every time you run the program.

## With synchronized:

Only one thread can execute run() at a time.
The increment (++counter) is protected.
The output is ordered and reliable, for example:

## Conclusion

This simple example shows why synchronization is essential in multithreaded programs.
Without it, shared variables can become inconsistent due to race conditions.
With it, execution is controlled and safe.