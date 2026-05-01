package com.java.poc.dsa.stack;

import java.util.LinkedList;
import java.util.Queue;

public class ImplementStackUsingQueue {

    private Queue<Integer> queue = new LinkedList<>();

    public ImplementStackUsingQueue() {

    }

    public void push(int x) {
        queue.add(x);
        for (int i = 1; i < queue.size(); i++) {
            queue.add(queue.remove());
        }

    }

    public int pop() {
        return queue.remove();
    }

    public int top() {
        return queue.peek();
    }

    public boolean empty() {
        return queue.isEmpty();
    }

    @Override
    public String toString() {
        return "ImplementStackUsingQueue{" +
                "queue=" + queue +
                '}';
    }

    public static void main(String[] args) {
        int val1 = 1;
        int val2 = 2;
        int val3 = 3;
        ImplementStackUsingQueue obj = new ImplementStackUsingQueue();
        obj.push(val1);
        obj.push(val2);
        obj.push(val3);
        System.out.println(obj.toString());
        int param_1 = obj.top();
        System.out.println(param_1);
        int param_2 = obj.pop();
        System.out.println(param_2);
        System.out.println(obj.toString());
    }
}
