package com.java.poc.common_patterns;

import java.util.PriorityQueue;

public class PriorityQueues {

    public static void main(String[] args) {
        int[] nums = {3,2,1,5,6,4};
        int k = 2;
        System.out.println(findKthLargest(nums, k));
    }

    public static int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        for (int num : nums) {
            pq.offer(num);
            if (pq.size() > k) {
                pq.poll(); // Remove smallest element to keep only 'k' largest
            }
        }

        return pq.peek(); // The root of the heap is the k-th largest
    }

}
