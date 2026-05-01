package com.java.poc.java_basics.threads;

public class ThreadLocalDemo {
        private static final ThreadLocal<Integer> threadLocalValue = ThreadLocal.withInitial(() -> 1);

        public static void main(String[] args) {
            Thread thread1 = new Thread(() -> {
                System.out.println("Thread 1 Initial Value: " + threadLocalValue.get());
                threadLocalValue.set(100);
                System.out.println("Thread 1 Updated Value: " + threadLocalValue.get());
            });

            Thread thread2 = new Thread(() -> {
                System.out.println("Thread 2 Initial Value: " + threadLocalValue.get());
                threadLocalValue.set(200);
                System.out.println("Thread 2 Updated Value: " + threadLocalValue.get());
            });

            thread1.start();
            thread2.start();
        }
}
