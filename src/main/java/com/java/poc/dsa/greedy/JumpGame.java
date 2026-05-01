package com.java.poc.dsa.greedy;

/**
 * 55. Jump Game
 * Medium
 * 16.8K
 * 891
 * Companies
 * You are given an integer array nums. You are initially positioned at the array's first index, and each element in the array represents your maximum jump length at that position.
 *
 * Return true if you can reach the last index, or false otherwise.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [2,3,1,1,4]
 * Output: true
 * Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.
 * Example 2:
 *
 * Input: nums = [3,2,1,0,4]
 * Output: false
 * Explanation: You will always arrive at index 3 no matter what. Its maximum jump length is 0, which makes it impossible to reach the last index.
 *
 *
 * Constraints:
 *
 * 1 <= nums.length <= 104
 * 0 <= nums[i] <= 105
 */
public class JumpGame {
    public static void main(String[] args) {
        int[] arr = new int[]{3,2,1,0,4};
        System.out.println("Can Jump ? : "+canJump(arr));
        System.out.println("Can Jump ? : "+canJump2(arr));
    }

    private static boolean canJump2(int[] nums) {
        int lastGoodIndex = 0;
        for(int i=0;i<=lastGoodIndex;i++){
            lastGoodIndex = Math.max(lastGoodIndex,i+nums[i]);
            if(lastGoodIndex>=nums.length-1){
                return true;
            }
        }

        return false;
    }

    private static boolean canJump(int[] nums) {
        int lastGoodIndex = nums.length-1;
        for(int i=nums.length-1;i>=0;i--){
            if(i+nums[i] >= lastGoodIndex){
                lastGoodIndex = i;
            }
        }

        return lastGoodIndex == 0;
    }
}
