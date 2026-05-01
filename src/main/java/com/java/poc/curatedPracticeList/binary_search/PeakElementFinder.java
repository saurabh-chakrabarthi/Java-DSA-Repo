package com.java.poc.curatedPracticeList.binary_search;

/**
 * 162. Find Peak Element
 * Medium
 * Topics
 * Companies
 * A peak element is an element that is strictly greater than its neighbors.
 *
 * Given a 0-indexed integer array nums, find a peak element, and return its index. If the array contains multiple peaks, return the index to any of the peaks.
 *
 * You may imagine that nums[-1] = nums[n] = -∞. In other words, an element is always considered to be strictly greater than a neighbor that is outside the array.
 *
 * You must write an algorithm that runs in O(log n) time.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [1,2,3,1]
 * Output: 2
 * Explanation: 3 is a peak element and your function should return the index number 2.
 * Example 2:
 *
 * Input: nums = [1,2,1,3,5,6,4]
 * Output: 5
 * Explanation: Your function can return either index number 1 where the peak element is 2, or index number 5 where the peak element is 6.
 *
 *
 * Constraints:
 *
 * 1 <= nums.length <= 1000
 * -231 <= nums[i] <= 231 - 1
 * nums[i] != nums[i + 1] for all valid i.
 */
public class PeakElementFinder {

    public int findPeakElement(int[] nums) {
        if (nums.length == 1) return 0; // single element

        int n = nums.length;

        // check if 0th/n-1th index is the peak element
        if (nums[0] > nums[1]) return 0;
        if (nums[n - 1] > nums[n - 2]) return n - 1;

        // search in the remaining array
        int start = 1;
        int end = n - 2;

        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] > nums[mid - 1] && nums[mid] > nums[mid + 1]) return mid;
            else if (nums[mid] < nums[mid - 1]) end = mid - 1;
            else start = mid + 1;
        }
        return -1; // dummy return statement
    }

    public static void main(String[] args) {
        PeakElementFinder finder = new PeakElementFinder();

        int[] nums1 = {1, 2, 3, 1};
        assert finder.findPeakElement(nums1) == 2 : "Test case 1 failed";

        int[] nums2 = {1, 2, 1, 3, 5, 6, 4};
        // This test case can return either 1 or 5 as both are correct answers.
        int result2 = finder.findPeakElement(nums2);
        assert result2 == 1 || result2 == 5 : "Test case 2 failed";

        System.out.println("All test cases passed!");
    }
}
