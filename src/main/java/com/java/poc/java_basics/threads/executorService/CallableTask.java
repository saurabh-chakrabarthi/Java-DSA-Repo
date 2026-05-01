package com.java.poc.java_basics.threads.executorService;

import java.util.Random;
import java.util.concurrent.Callable;

public class CallableTask implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.out.println("Thread Name : "+Thread.currentThread().getName());
        Thread.sleep(5000);
        return new Random().nextInt();
    }
}
