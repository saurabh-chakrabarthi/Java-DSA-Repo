package com.java.poc.dsa.array_and_hashing;

import java.util.Arrays;

/**
 * 238. Product of Array Except Self
 * Medium
 * 16.3K
 * 906
 * Companies
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
        int[] nums1 = new int [] {1,2,3,4};
        System.out.println("Product of Array except self is : "+ Arrays.toString(productExceptSelf1(nums1)));

        int[] nums2 = new int [] {1,2,3,4};
        System.out.println("Product of Array except self is : "+ Arrays.toString(productExceptSelf2(nums2)));
    }

    public static int[] productExceptSelf1(int[] nums) {
        int product = 1;
        for(int i=0;i<nums.length;i++){
            product = product * nums[i];
        }

        for(int j=0;j<nums.length;j++){
            int tmp = nums[j];
            nums[j] = product/tmp;
        }

        return nums;
    }

    public static int[] productExceptSelf2(int[] nums) {

        int n = nums.length;
        int pre[] = new int[n];
        int suff[] = new int[n];
        pre[0] = 1;
        suff[n - 1] = 1;

        for(int i = 1; i < n; i++) {
            pre[i] = pre[i - 1] * nums[i - 1];
        }
        for(int i = n - 2; i >= 0; i--) {
            suff[i] = suff[i + 1] * nums[i + 1];
        }

        int ans[] = new int[n];
        for(int i = 0; i < n; i++) {
            ans[i] = pre[i] * suff[i];
        }
        return ans;
    }
}
