package com.java.poc.java_basics.threads.executorService;

public class RunnableTask implements Runnable {
    @Override
    public void run() {
        System.out.println("Thread Name : "+Thread.currentThread().getName());
    }
}
