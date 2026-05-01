package com.java.poc.java_basics.threads.executorService;

import java.util.concurrent.*;

public class ScheduledThreadPoolDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ScheduledExecutorService service = Executors.newScheduledThreadPool(10);

        // RunnableTask to run after 10 second delay
        service.schedule(new RunnableTask(),10,TimeUnit.SECONDS);

        //task to run every 10 seconds
        service.scheduleAtFixedRate(new RunnableTask(), 15,10, TimeUnit.SECONDS);

        //task to run repeatedly 10 seconds after previous task completed
        service.scheduleWithFixedDelay(new RunnableTask(),15,10,TimeUnit.SECONDS);

        service.shutdown();
    }
}


