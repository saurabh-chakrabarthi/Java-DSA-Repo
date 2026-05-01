package com.java.poc.common_patterns;

public class Kadane {

    public static void main(String[] args) {
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println(maxSubArray(nums));
    }

    public static int maxSubArray(int[] nums) {
        int currentMax = nums[0], globalMax = nums[0];

        for (int i = 1; i < nums.length; i++) {
            currentMax = Math.max(nums[i], currentMax + nums[i]); // Max of current element or extending subarray
            globalMax = Math.max(globalMax, currentMax); // Track the global maximum
        }

        return globalMax;
    }

}
