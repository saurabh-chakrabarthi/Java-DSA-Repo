package com.java.poc.java_basics.threads.waitNotify;

public class ProducerThread implements Runnable {
    Counter count;

    public ProducerThread(Counter count) {
        this.count = count;
        Thread t2 = new Thread(this, "Producer Thread");
        t2.start();
    }

    @Override
    public void run() {
        System.out.println("Producer Thread Started ...");

        for (int i = 0; i < 10; i++) {
            count.setCount();
            try {
                Thread.sleep(1);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

    }
}
