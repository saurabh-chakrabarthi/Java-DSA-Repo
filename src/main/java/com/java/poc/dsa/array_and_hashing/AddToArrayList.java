package com.java.poc.dsa.array_and_hashing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 989. Add to Array-Form of Integer
 * Solved
 * Easy
 * Topics
 * Companies
 * The array-form of an integer num is an array representing its digits in left to right order.
 *
 * For example, for num = 1321, the array form is [1,3,2,1].
 * Given num, the array-form of an integer, and an integer k, return the array-form of the integer num + k.
 *
 *
 *
 * Example 1:
 *
 * Input: num = [1,2,0,0], k = 34
 * Output: [1,2,3,4]
 * Explanation: 1200 + 34 = 1234
 * Example 2:
 *
 * Input: num = [2,7,4], k = 181
 * Output: [4,5,5]
 * Explanation: 274 + 181 = 455
 * Example 3:
 *
 * Input: num = [2,1,5], k = 806
 * Output: [1,0,2,1]
 * Explanation: 215 + 806 = 1021
 *
 *
 * Constraints:
 *
 * 1 <= num.length <= 104
 * 0 <= num[i] <= 9
 * num does not contain any leading zeros except for the zero itself.
 * 1 <= k <= 104
 */
public class AddToArrayList {

    public static void main(String[] args) {
        int[] num = {1, 2, 3, 4, 5};
        int k = 12;
        System.out.println(addToArrayForm(num, k));
    }

    public static List<Integer> addToArrayForm(int[] num, int k) {
        List<Integer> result = new ArrayList<>();
        int n = num.length;
        int i = n - 1;

        while (i >= 0 || k > 0) {
            // If there are still digits in num, add them to k
            if (i >= 0) {
                k += num[i];
                i--;
            }

            // Add the least significant digit of k to the result
            result.add(k % 10);

            // Update k by removing the least significant digit
            k /= 10;
        }

        // Since we are adding digits from the end, reverse the result
        Collections.reverse(result);

        return result;
    }
}
