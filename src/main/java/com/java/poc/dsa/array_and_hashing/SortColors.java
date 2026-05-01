package com.java.poc.dsa.array_and_hashing;

import java.util.Arrays;

/**
 * 75. Sort Colors
 * Medium
 * 13.8K
 * 501
 * Companies
 * Given an array nums with n objects colored red, white, or blue, sort them in-place so that objects of the same color are adjacent, with the colors in the order red, white, and blue.
 *
 * We will use the integers 0, 1, and 2 to represent the color red, white, and blue, respectively.
 *
 * You must solve this problem without using the library's sort function.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [2,0,2,1,1,0]
 * Output: [0,0,1,1,2,2]
 * Example 2:
 *
 * Input: nums = [2,0,1]
 * Output: [0,1,2]
 *
 *
 * Constraints:
 *
 * n == nums.length
 * 1 <= n <= 300
 * nums[i] is either 0, 1, or 2.
 *
 *
 * Follow up: Could you come up with a one-pass algorithm using only constant extra space?
 */
public class SortColors {
    public static void main(String[] args) {
        int[] arr = new int[]{1,2,3,3,4,3,4,2,1};
        System.out.println("Array elements before sorting: " + Arrays.toString(arr));
        sortColors(arr);
        System.out.println("Array elements after sorting: " + Arrays.toString(arr));
        sortColors2(arr);
    }

    public static void sortColors(int[] nums) {
        boolean swapped = true;
        int j = 0;
        int tmp;
        while (swapped) {
            swapped = false;
            j++;
            for (int i = 0; i < nums.length - j; i++) {
                if (nums[i] > nums[i + 1]) {
                    tmp = nums[i];
                    nums[i] = nums[i + 1];
                    nums[i + 1] = tmp;
                    swapped = true;
                }
            }
        }
    }

    public static void sortColors2(int[] nums) {
        int low = 0, mid = 0, high = nums.length - 1;

        while (mid <= high) {
            if (nums[mid] == 0) {
                swap(nums, low, mid);
                low++;
                mid++;
            } else if (nums[mid] == 1) {
                mid++;
            } else { // nums[mid] == 2
                swap(nums, mid, high);
                high--;
            }
        }
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
