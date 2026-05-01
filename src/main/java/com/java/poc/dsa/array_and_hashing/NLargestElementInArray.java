package com.java.poc.dsa.array_and_hashing;

import java.util.Arrays;
import java.util.PriorityQueue;

public class NLargestElementInArray {
    public static void main(String[] args) {
        int n = 5;
        int nums[] = new int[]{6, 3, 1, 8, 9, 2, 4, 7, 10, 5};
        //kthLargestElement1(nums,n);
        kthLargestElement2(nums, n);

    }


    private static void kthLargestElement1(int[] nums, int n) {
        System.out.println(Arrays.toString(nums));
        Arrays.sort(nums);
        System.out.println(Arrays.toString(nums));
        System.out.println(n + "th largest element in the array is :: " + nums[nums.length - n]);
    }

    private static void kthLargestElement2(int[] nums, int n) {
        System.out.println(Arrays.toString(nums));
        PriorityQueue<Integer> q = new PriorityQueue<Integer>(n);
        for (int i : nums) {
            q.add(i);
            System.out.println(q.toString());
            if (q.size() > n) {
                q.poll();
            }
        }

        System.out.println(n + "th largest element in the array is :: " + q.peek());
    }
}
