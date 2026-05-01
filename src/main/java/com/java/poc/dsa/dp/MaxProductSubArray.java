package com.java.poc.dsa.dp;

/**
 * Given an integer array nums, find a subarray that has the largest product, and return the product.
 *
 * The test cases are generated so that the answer will fit in a 32-bit integer.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [2,3,-2,4]
 * Output: 6
 * Explanation: [2,3] has the largest product 6.
 * Example 2:
 *
 * Input: nums = [-2,0,-1]
 * Output: 0
 * Explanation: The result cannot be 2, because [-2,-1] is not a subarray.
 *
 *
 * Constraints:
 *
 * 1 <= nums.length <= 2 * 104
 * -10 <= nums[i] <= 10
 * The product of any subarray of nums is guaranteed to fit in a 32-bit integer.
 */
public class MaxProductSubArray {
    public static void main(String[] args) {
        int[] nums1 = {2, 3, -2, 4};
        System.out.println(maxProduct(nums1)); // Output: 6

        int[] nums2 = {-2, 0, -1};
        System.out.println(maxProductBruteForce(nums2)); // Output: 0
    }

    public static int maxProduct(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int maxProduct = nums[0];
        int minProduct = nums[0];
        int result = nums[0];

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < 0) {
                // Swap max and min when a negative number is encountered
                int temp = maxProduct;
                maxProduct = minProduct;
                minProduct = temp;
            }

            maxProduct = Math.max(nums[i], maxProduct * nums[i]);
            minProduct = Math.min(nums[i], minProduct * nums[i]);

            result = Math.max(result, maxProduct);
        }

        return result;

    }

    public static int maxProductBruteForce(int[] nums) {

        int max_product = nums[0];
        for(int i=0;i<nums.length;i++){
            int current_product = 1;
            for(int j=i;j<nums.length;j++){
                current_product *= nums[j];
                max_product = Math.max(max_product,current_product);
            }
        }
        return max_product;
    }
}
