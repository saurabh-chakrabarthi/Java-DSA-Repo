package com.java.poc.curatedPracticeList.blind75;

import java.util.Arrays;

/**
 * 238. Product of Array Except Self
 * Solved
 * Medium
 * Topics
 * Companies
 * Hint
 * Given an integer array nums, return an array answer such that answer[i] is equal to the product of all the elements of nums except nums[i].
 *
 * The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer.
 *
 * You must write an algorithm that runs in O(n) time and without using the division operation.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [1,2,3,4]
 * Output: [24,12,8,6]
 * Example 2:
 *
 * Input: nums = [-1,1,0,-3,3]
 * Output: [0,0,9,0,0]
 *
 *
 * Constraints:
 *
 * 2 <= nums.length <= 105
 * -30 <= nums[i] <= 30
 * The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer.
 *
 *
 * Follow up: Can you solve the problem in O(1) extra space complexity? (The output array does not count as extra space for space complexity analysis.)
 */
public class ProductOfArrayExceptSelf {

    public static void main(String[] args) {
        int[] nums = new int[]{1,2,3,4};
        System.out.println(Arrays.toString(productExceptSelf(nums)));

        System.out.println(Arrays.toString(productExceptSelf2(nums)));
    }


    public static int[] productExceptSelf(int[] nums) {
        int[] result = new int[nums.length];

        for(int i=0; i<nums.length;i++){
            int prod = 1;
            for(int j=0;j<nums.length;j++){
                if(i != j){
                    prod*=nums[j];
                }
            }
            result[i] = prod;
        }

        return result;
    }


    public static int[] productExceptSelf2(int[] nums) {
        int[] result = new int[nums.length];
        int[] left = new int[nums.length];
        int[] right = new int[nums.length];

        left[0] = 1;
        right[nums.length-1] = 1;

        for(int i=1;i<nums.length;i++){
            left[i] = left[i-1] * nums[i-1];
        }

        for(int j=nums.length-2;j>=0;j--){
            right[j] = right[j+1] * nums[j+1];
        }

        for(int k=0;k<nums.length;k++){
            result[k] = left[k] * right[k];
        }

        return result;
    }
}
