package com.java.poc.common_patterns;

public class Greedy {

    public static void main(String[] args) {
        int[] nums = {2, 3, 1, 1, 4};
        System.out.println(canJump(nums));
    }

    public static boolean canJump(int[] nums) {
        int maxReach = 0;

        for (int i = 0; i < nums.length; i++) {
            if (i > maxReach) return false; // If current index is out of reach
            maxReach = Math.max(maxReach, i + nums[i]); // Update max reachable index
        }

        return true;
    }

}
