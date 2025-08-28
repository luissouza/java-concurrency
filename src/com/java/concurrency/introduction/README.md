# Creating Simple Threads in Java

This code demonstrates **two basic ways to create threads in Java**:

1.  **By implementing the `Runnable` interface**
2.  **By extending the `Thread` class**

------------------------------------------------------------------------

## Code Explanation

### 1. `MyThreadWithRunnable` (Using Runnable)

``` java
static class MyThreadWithRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Class MyThread implementing Runnable: " + Thread.currentThread().getName());
    }
}
```

-   This class implements the `Runnable` interface.
-   The `run()` method contains the code that will run when the thread starts.
-   We create a `Thread` object and pass an instance of this class to
    it.

Example:

``` java
Thread t1 = new Thread(new MyThreadWithRunnable());
t1.start();
```

------------------------------------------------------------------------

### 2. `MyThread` (Extending Thread)

``` java
static class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("Class MyThread extending Thread: " + Thread.currentThread().getName());
    }
}
```

-   This class **extends** the built-in `Thread` class.
-   It overrides the `run()` method, just like with `Runnable`.
-   To start it, you simply create an object and call `start()`.

Example:

``` java
MyThread t2 = new MyThread();
t2.start();
```

------------------------------------------------------------------------

### 3. Main Method

``` java
public static void main(String[] args) {
    System.out.println("Current Thread: " + Thread.currentThread().getName());

    Thread t1 = new Thread(new MyThreadWithRunnable()); // Runnable example
    MyThread t2 = new MyThread(); // Extending Thread example

    t1.start();
    t2.start();
}
```

-   The program first prints the **current thread name**, which is
    usually `"main"`.\
-   Then it creates two new threads:
    -   `t1`: Created using the `Runnable` interface.\
    -   `t2`: Created by extending the `Thread` class.\
-   Both threads are started with `.start()`, which causes the `run()`
    method to execute in **parallel**.

------------------------------------------------------------------------

## Sample Output

The output will look like this (order may change because threads run
independently):

    Current Thread: main
    Class MyThread implementing Runnable: Thread-0
    Class MyThread extending Thread: Thread-1

Note: Thread names (`Thread-0`, `Thread-1`) may differ.

------------------------------------------------------------------------

## When use Runnable vs Thread?

-   Use **Runnable** when your class needs to extend another class
    (since Java doesn't support multiple inheritance).\
-   Use **Thread** if you just want a simple implementation and don't
    need to extend another class.

------------------------------------------------------------------------
