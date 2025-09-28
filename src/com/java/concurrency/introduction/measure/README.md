# Java Multithreading - Measure how many threads can you create

## About 

In this class, we explore how to measure the maximum threads your system can handle.

## Result

```java
Reached thread limit: 17214
java.lang.OutOfMemoryError: unable to create native thread: possibly out of
memory or process/resource limits reached
at java.base/java.lang.Thread.start0(Native Method)
at java.base/java.lang.Thread.start(Thread.java:1526)
at com.java.concurrency.introduction.measure.ThreadLimitMeasureTest.main(ThreadLimitMeasureTest.java:16)
```