package com.java.poc.java_basics.threads.waitNotify;

class Counter {

    boolean state = true;
    private int count = 0;

    public synchronized void getCount() {
        while (!state) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Get : " + count);
        state = false;
        notify();
    }

    public synchronized void setCount() {
        while (state) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        count++;
        System.out.println("Set : " + count);
        state = true;
        notify();
    }
}
