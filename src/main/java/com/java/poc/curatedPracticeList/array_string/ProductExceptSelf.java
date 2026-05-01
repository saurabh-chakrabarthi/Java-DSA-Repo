package com.java.poc.curatedPracticeList.array_string;

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
public class ProductExceptSelf {

    public int[] calculateProduct(int[] nums) {
        int length = nums.length;
        int[] result = new int[length];

        int leftMultiplier = 1;
        for (int idx = 0; idx < length; idx++) {
            if (idx > 0) {
                leftMultiplier = leftMultiplier * nums[idx - 1];
            }
            result[idx] = leftMultiplier;
        }

        int rightMultiplier = 1;
        for (int idx = length - 1; idx >= 0; idx--) {
            if (idx < length - 1) {
                rightMultiplier = rightMultiplier * nums[idx + 1];
            }
            result[idx] *= rightMultiplier;
        }

        return result;
    }

    public static void main(String[] args) {

        ProductExceptSelf calculator = new ProductExceptSelf();

        int[] testInput1 = {1,2,3,4};
        int[] expectedOutput1 = {24,12,8,6};
        assert java.util.Arrays.equals(calculator.calculateProduct(testInput1), expectedOutput1) : "Test case 1 failed";

        int[] testInput2 = {-1,1,0,-3,3};
        int[] expectedOutput2 = {0,0,9,0,0};
        assert java.util.Arrays.equals(calculator.calculateProduct(testInput2), expectedOutput2) : "Test case 2 failed";

        System.out.println("All test cases passed!");
    }
}
