package com.java.poc.java_basics.threads.blockingQueueImpl;

import java.util.concurrent.ArrayBlockingQueue;

public class Java8BlockingQueue {

    public static void main(String[] args) throws InterruptedException {

        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);

        final Runnable producer = () -> {
                try {
                    for (int i = 0; i < 10; i++) {
                        System.out.println("Producer is putting - " + i);
                        queue.put(i);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        };

        Thread t1 = new Thread(producer, "T1");
        System.out.println(queue.size());
        t1.start();
        t1.sleep(5000);
        System.out.println(queue.size());


        final Runnable consumer = () -> {
            while (true) {
                try {
                    System.out.println("Consumer is taking - " + queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread t2 = new Thread(consumer);
        System.out.println(queue.size());
        t2.start();
        System.out.println(queue.size());
        t2.sleep(500);
    }
}
