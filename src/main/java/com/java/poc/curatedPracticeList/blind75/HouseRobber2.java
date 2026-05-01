package com.java.poc.curatedPracticeList.blind75;

/**
 * 213. House Robber II
 * Medium
 * Topics
 * Companies
 * Hint
 * You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed. All houses at this place are arranged in a circle. That means the first house is the neighbor of the last one. Meanwhile, adjacent houses have a security system connected, and it will automatically contact the police if two adjacent houses were broken into on the same night.
 *
 * Given an integer array nums representing the amount of money of each house, return the maximum amount of money you can rob tonight without alerting the police.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [2,3,2]
 * Output: 3
 * Explanation: You cannot rob house 1 (money = 2) and then rob house 3 (money = 2), because they are adjacent houses.
 * Example 2:
 *
 * Input: nums = [1,2,3,1]
 * Output: 4
 * Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
 * Total amount you can rob = 1 + 3 = 4.
 * Example 3:
 *
 * Input: nums = [1,2,3]
 * Output: 3
 *
 *
 * Constraints:
 *
 * 1 <= nums.length <= 100
 * 0 <= nums[i] <= 1000
 */
public class HouseRobber2 {
    public static void main(String[] args) {
        System.out.println(rob(new int[]{2,7,9,3,1}));
    }

    public static int rob(int[] nums) {
        if(nums.length<2){
            return nums[0];
        }

        int[] dp1 = new int[nums.length];
        int[] dp2 = new int[nums.length];

        for(int i=0;i<nums.length-1;i++){
            dp1[i] = nums[i];
            dp2[i+1] = nums[i+1];
        }
        int loot1 = robHelper(dp1);
        int loot2 = robHelper(dp2);

        return Math.max(loot1,loot2);
    }

    public static int robHelper(int[] nums){

        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0],nums[1]);
        for(int i=2;i<nums.length;i++){
            dp[i] = Math.max(dp[i-2]+nums[i],dp[i-1]);
        }

        return dp[nums.length-1];
    }
}
