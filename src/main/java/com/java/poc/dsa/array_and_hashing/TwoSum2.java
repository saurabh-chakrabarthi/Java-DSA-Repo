package com.java.poc.dsa.array_and_hashing;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 167. Two Sum II - Input Array Is Sorted
 * Medium
 * 8.8K
 * 1.2K
 * Companies
 * Given a 1-indexed array of integers numbers that is already sorted in non-decreasing order, find two numbers such that they add up to a specific target number. Let these two numbers be numbers[index1] and numbers[index2] where 1 <= index1 < index2 <= numbers.length.
 *
 * Return the indices of the two numbers, index1 and index2, added by one as an integer array [index1, index2] of length 2.
 *
 * The tests are generated such that there is exactly one solution. You may not use the same element twice.
 *
 * Your solution must use only constant extra space.
 *
 *
 *
 * Example 1:
 *
 * Input: numbers = [2,7,11,15], target = 9
 * Output: [1,2]
 * Explanation: The sum of 2 and 7 is 9. Therefore, index1 = 1, index2 = 2. We return [1, 2].
 * Example 2:
 *
 * Input: numbers = [2,3,4], target = 6
 * Output: [1,3]
 * Explanation: The sum of 2 and 4 is 6. Therefore index1 = 1, index2 = 3. We return [1, 3].
 * Example 3:
 *
 * Input: numbers = [-1,0], target = -1
 * Output: [1,2]
 * Explanation: The sum of -1 and 0 is -1. Therefore index1 = 1, index2 = 2. We return [1, 2].
 *
 *
 * Constraints:
 *
 * 2 <= numbers.length <= 3 * 104
 * -1000 <= numbers[i] <= 1000
 * numbers is sorted in non-decreasing order.
 * -1000 <= target <= 1000
 * The tests are generated such that there is exactly one solution.
 */
public class TwoSum2 {

    public static void main(String[] args) {
        int[] numbers = new int[]{1,2,3,4};
        int target = 7;
        System.out.println(Arrays.toString(twoSum_bf(numbers,target)));
        System.out.println(Arrays.toString(twoSum_bs(numbers,target)));
        System.out.println(Arrays.toString(twoSum_hash(numbers,target)));
        System.out.println(Arrays.toString(twoSum_tp(numbers,target)));
    }

    public static int[] twoSum_bf(int[] numbers, int target) {
        int len = numbers.length;
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                if (numbers[i] + numbers[j] == target) {
                    return new int[]{i + 1, j + 1};
                }
            }
        }

        return new int[0];
    }

    public static int[] twoSum_hash(int[] numbers, int target) {
        int len = numbers.length;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < len; i++) {
            if (map.containsKey(target - numbers[i])) {
                return new int[]{map.get(target - numbers[i]), i + 1};
            }

            map.putIfAbsent(numbers[i], i + 1);
        }

        return new int[0];
    }

    public static int[] twoSum_bs(int[] numbers, int target) {
        for (int i = 0; i < numbers.length; ++i) {
            int low = i + 1;
            int high = numbers.length - 1;
            while (low <= high) {
                int mid = (high - low) / 2 + low;
                if (numbers[mid] == target - numbers[i]) {
                    return new int[]{i + 1, mid + 1};
                } else if (numbers[mid] > target - numbers[i]) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }
        }
        return new int[]{-1, -1};
    }

    public static int[] twoSum_tp(int[] numbers, int target) {
        int len = numbers.length;
        int left = 0;
        int right = len - 1;
        while (left < right) {
            if (numbers[left] + numbers[right] > target) {
                right--;
            } else if (numbers[left] + numbers[right] < target) {
                left++;
            } else {
                return new int[]{left + 1, right + 1};
            }
        }

        return new int[]{-1, -1};
    }
}
