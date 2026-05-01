package com.java.poc.dsa.array_and_hashing;

import java.util.Arrays;
import java.util.BitSet;

/**
 * 268. Missing Number
 * Easy
 * 8.5K
 * 3K
 * Companies
 * Given an array nums containing n distinct numbers in the range [0, n], return the only number in the range that is missing from the array.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [3,0,1]
 * Output: 2
 * Explanation: n = 3 since there are 3 numbers, so all numbers are in the range [0,3]. 2 is the missing number in the range since it does not appear in nums.
 * Example 2:
 *
 * Input: nums = [0,1]
 * Output: 2
 * Explanation: n = 2 since there are 2 numbers, so all numbers are in the range [0,2]. 2 is the missing number in the range since it does not appear in nums.
 * Example 3:
 *
 * Input: nums = [9,6,4,2,3,5,7,0,1]
 * Output: 8
 * Explanation: n = 9 since there are 9 numbers, so all numbers are in the range [0,9]. 8 is the missing number in the range since it does not appear in nums.
 *
 *
 * Constraints:
 *
 * n == nums.length
 * 1 <= n <= 104
 * 0 <= nums[i] <= n
 * All the numbers of nums are unique.
 */
public class MissingNumberInArray {

    public static void main(String[] args) {

        int[] nums = {-2,0,1,1,3,4,5};
        int n = 16;

        //System.out.println("The missing number is : "+missingNumber(nums,n));

        System.out.println("The missing numbers are : ");
        printMissingNumber(nums,n);
    }

    public int missingNumber(int[] nums) {
        int n = nums.length;
        int sum = (n*(n+1))/2;

        for(int i : nums){
            sum = sum - i;
        }

        return sum;
    }

    /**
     * A general method to find missing values from an integer array in Java.
     * This method will work even if array has more than one missing element.
     */
    private static void printMissingNumber(int[] numbers, int count) {
        int missingCount = count - numbers.length;
        BitSet bitSet = new BitSet(count);

        for (int number : numbers) {
            bitSet.set(number - 1);
        }

        System.out.printf("Missing numbers in integer array %s, with total number %d is %n",
                Arrays.toString(numbers), count);
        int lastMissingIndex = 0;

        for (int i = 0; i < missingCount; i++) {
            lastMissingIndex = bitSet.nextClearBit(lastMissingIndex);
            System.out.println(++lastMissingIndex);
        }

    }

}
