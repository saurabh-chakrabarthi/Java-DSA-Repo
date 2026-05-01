package com.java.poc.common_patterns;

public class DP {

    public static void main(String[] args) {
        int[] nums = {2, 7, 9, 3, 1};
        System.out.println(maxSumNonAdjacent(nums));
    }

    public static int maxSumNonAdjacent(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];

        int prev1 = 0, prev2 = 0;

        for (int num : nums) {
            int temp = prev1;
            prev1 = Math.max(prev1, prev2 + num); // Max of including or not including current element
            prev2 = temp;
        }

        return prev1;
    }

}
