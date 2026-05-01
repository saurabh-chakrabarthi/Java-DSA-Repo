package com.java.poc.dsa.slidingWindow;

/**
 * 643. Maximum Average Subarray I
 * Easy
 * Topics
 * Companies
 * You are given an integer array nums consisting of n elements, and an integer k.
 *
 * Find a contiguous subarray whose length is equal to k that has the maximum average value and return this value. Any answer with a calculation error less than 10-5 will be accepted.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [1,12,-5,-6,50,3], k = 4
 * Output: 12.75000
 * Explanation: Maximum average is (12 - 5 - 6 + 50) / 4 = 51 / 4 = 12.75
 * Example 2:
 *
 * Input: nums = [5], k = 1
 * Output: 5.00000
 *
 *
 * Constraints:
 *
 * n == nums.length
 * 1 <= k <= n <= 105
 * -104 <= nums[i] <= 104
 */
public class MaxAvgSubarray {

    public static void main(String[] args) {
        int[] nums = new int[]{1,12,-5,-6,50,3};
        int k = 4;

        System.out.println("Max Avg is : " +findMaxAvg(nums,k));
    }

    public static double findMaxAvg(int[] nums,int k){

        int sum=0;
        for(int i=0;i<k;i++){
            sum+= nums[i];
        }

        int maxSum = sum;
        int start = 0;
        int end = k;

        while(end<nums.length){
            sum = sum - nums[start];
            start++;

            sum = sum + nums[end];
            end++;

            maxSum = Math.max(sum,maxSum);
        }

        return (double) maxSum/k;
    }
}
