package com.java.poc.java_basics.threads.executorService;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SingleThreadedExecutorDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService service = Executors.newFixedThreadPool(2);
        Future future;

        for (int i = 0; i < 100; i++) {
            future =  service.submit(new RunnableTask());
            Thread.sleep(1000);
            //System.out.println(future.get());
        }
        System.out.println("Thread Name : " + Thread.currentThread().getName());
        service.shutdown();
    }
}


