package com.java.poc.dsa.string;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Given an unsorted integer array, find the smallest missing positive integer.
 * You must implement an algorithm that runs in O(n) time and uses constant extra space.
 * Example 1:
 * Input: nums = [1,2,0]
 * Output: 3
 * Example 2:
 * Input: nums = [3,4,-1,1]
 * Output: 2
 * Example 3:
 * Input: nums = [7,8,9,11,12]
 *
 */
public class FindSmallestPositiveMissingNumber {
    public static void main(String[] args) {
        int[] arr = {1,2,6,4,1,2};
        System.out.println("Smallest positive missing number is: " + findSmallestPositiveMissingNumber(arr));
    }

    private static int findSmallestPositiveMissingNumber(int[] arr) {
        // Step 1: Sort the array
        Arrays.sort(arr);

        // Step 2: Add all positive numbers to a HashSet
        HashSet<Integer> set = new HashSet<>();
        for (int num : arr) {
            if (num > 0) {
                set.add(num);
            }
        }

        // Step 3: Find the smallest missing positive number
        int smallestMissing = 1;
        while (set.contains(smallestMissing)) {
            smallestMissing++;
        }

        return smallestMissing;
    }
}
