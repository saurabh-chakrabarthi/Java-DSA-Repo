package com.java.poc.curatedPracticeList.bit_manipulation;

/**
 * 338. Counting Bits
 * Easy
 * Topics
 * Companies
 * Hint
 * Given an integer n, return an array ans of length n + 1 such that for each i (0 <= i <= n), ans[i] is the number of 1's in the binary representation of i.
 *
 *
 *
 * Example 1:
 *
 * Input: n = 2
 * Output: [0,1,1]
 * Explanation:
 * 0 --> 0
 * 1 --> 1
 * 2 --> 10
 * Example 2:
 *
 * Input: n = 5
 * Output: [0,1,1,2,1,2]
 * Explanation:
 * 0 --> 0
 * 1 --> 1
 * 2 --> 10
 * 3 --> 11
 * 4 --> 100
 * 5 --> 101
 *
 *
 * Constraints:
 *
 * 0 <= n <= 105
 */
public class BitCounter {

    public int[] countBits(int num) {
        int[] result = new int[num + 1];
        for (int i = 1; i <= num; i++) {
            result[i] = result[i >> 1] + (i & 1);
        }
        return result;
    }

    public static void main(String[] args) {
        BitCounter bitCounter = new BitCounter();

        assert java.util.Arrays.equals(bitCounter.countBits(2), new int[]{0, 1, 1}) : "Test case 1 failed";
        assert java.util.Arrays.equals(bitCounter.countBits(5), new int[]{0, 1, 1, 2, 1, 2}) : "Test case 2 failed";

        System.out.println("All test cases passed!");
    }
}
