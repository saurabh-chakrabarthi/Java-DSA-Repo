package com.java.poc.curatedPracticeList.array_string;

/**
 * 334. Increasing Triplet Subsequence
 * Medium
 * Topics
 * Companies
 * Given an integer array nums, return true if there exists a triple of indices (i, j, k) such that i < j < k and nums[i] < nums[j] < nums[k]. If no such indices exists, return false.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [1,2,3,4,5]
 * Output: true
 * Explanation: Any triplet where i < j < k is valid.
 * Example 2:
 *
 * Input: nums = [5,4,3,2,1]
 * Output: false
 * Explanation: No triplet exists.
 * Example 3:
 *
 * Input: nums = [2,1,5,0,4,6]
 * Output: true
 * Explanation: The triplet (3, 4, 5) is valid because nums[3] == 0 < nums[4] == 4 < nums[5] == 6.
 *
 *
 * Constraints:
 *
 * 1 <= nums.length <= 5 * 105
 * -231 <= nums[i] <= 231 - 1
 *
 *
 * Follow up: Could you implement a solution that runs in O(n) time complexity and O(1) space complexity?
 */
public class IncreasingTriplet {

    public boolean hasIncreasingTriplet(int[] numbers) {
        int firstMin = Integer.MAX_VALUE, secondMin = Integer.MAX_VALUE;
        for (int number : numbers) {
            if (number <= firstMin) {
                firstMin = number;
            } else if (number <= secondMin) {
                secondMin = number;
            } else {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {

        IncreasingTriplet checker = new IncreasingTriplet();

        int[] testInput1 = {1,2,3,4,5};
        assert checker.hasIncreasingTriplet(testInput1) == true : "Test case 1 failed";

        int[] testInput2 = {5,4,3,2,1};
        assert checker.hasIncreasingTriplet(testInput2) == false : "Test case 2 failed";

        int[] testInput3 = {2,1,5,0,4,6};
        assert checker.hasIncreasingTriplet(testInput3) == true : "Test case 3 failed";

        System.out.println("All test cases passed!");
    }
}
