package com.java.poc.java_basics.threads.customBlockingQueue;

import java.util.LinkedList;
import java.util.Queue;

public class BlockingQueueImplWaitNotify<E> {
    private Queue<E> queue;
    private int max = 16;
    private Object notEmpty = new Object();
    private Object notFull = new Object();


    public BlockingQueueImplWaitNotify(int size) {
        queue = new LinkedList<>();
        this.max = size;
    }


    public synchronized void put(E e) {
        try {
            if (queue.size() == max) {
                notFull.wait();
            }
            queue.add(e);
            notEmpty.notifyAll();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public synchronized E take() {
        E item = null;
        try {
            while (queue.size() == 0) {
                notEmpty.wait();
            }
            item = queue.remove();
            notFull.notifyAll();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return item;
    }
}
