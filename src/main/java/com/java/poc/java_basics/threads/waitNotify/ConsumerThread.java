package com.java.poc.java_basics.threads.waitNotify;

public class ConsumerThread implements Runnable {
    Counter count;

    public ConsumerThread(Counter count) {
        this.count = count;
        Thread t1 = new Thread(this, "Consumer Thread");
        t1.start();
    }

    @Override
    public void run() {
        System.out.println("Consumer Thread Started ...");

        for (int i = 0; i < 10; i++) {
            count.getCount();
            try {
                Thread.sleep(1);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
