package com.java.poc.java_basics.threads.waitNotify;

public class Controller {

    public static void main(String[] args) {
        Counter count = new Counter();
        ConsumerThread ct = new ConsumerThread(count);
        ProducerThread pt = new ProducerThread(count);
    }
}
