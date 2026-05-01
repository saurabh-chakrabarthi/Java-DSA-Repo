package com.java.poc.dsa.queue;

import java.util.Arrays;
import java.util.PriorityQueue;

public class PriorityQueueTest {
    public static void main(String args[]) {

        int nums[] = new int[]{6, 3, 1, 8, 9, 2, 4, 7, 10, 5};
        // Creating empty priority queue
        PriorityQueue<Integer> pQueue = new PriorityQueue<Integer>();

        System.out.println("Array : " + Arrays.toString(nums));
        for (int i : nums) {
            // Adding items to the pQueue using add()
            pQueue.add(i);
            System.out.println(pQueue.toString());
        }

        // Printing the PriorityQueueTest
        System.out.println("pQueue : " + pQueue);

        // Printing the top element of PriorityQueueTest
        System.out.println(pQueue.peek());

        // Printing the top element and removing it
        // from the PriorityQueueTest container
        System.out.println(pQueue.poll());

        // Printing the Priority Queue again
        System.out.println(pQueue);

        // Printing the top element again
        System.out.println(pQueue.peek());
    }
}