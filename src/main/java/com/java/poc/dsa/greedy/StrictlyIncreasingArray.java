package com.java.poc.dsa.greedy;

public class StrictlyIncreasingArray {
    public static void main(String[] args) {
        int[] nums = new int[]{3,2,3};
        System.out.println("min operations : "+minOperations(nums));
    }

    public static int minOperations(int[] nums) {
        int count = 0;
        for(int i=0;i<nums.length-1;i++){
            if (nums[i] >= nums[i + 1]) {
                int diff = nums[i] + 1;
                count += diff - nums[i+1];
                nums[i+1] = diff;
            }
        }
        return count;
    }
}
