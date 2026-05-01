package com.java.poc.java_basics.threads.executorService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class FixedSizeThreadPoolDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        int coreCount = Runtime.getRuntime().availableProcessors();
        ExecutorService service = Executors.newFixedThreadPool(coreCount);
        Future<Integer> future;
        List<Future> allFutures = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            service.execute(new RunnableTask());
            future =  service.submit(new CallableTask());
            allFutures.add(future);
            System.out.println("Future returned is " +future.get());
        }
        System.out.println(allFutures.toString());
        System.out.println("Thread Name : " + Thread.currentThread().getName());
        service.shutdown();
    }
}

