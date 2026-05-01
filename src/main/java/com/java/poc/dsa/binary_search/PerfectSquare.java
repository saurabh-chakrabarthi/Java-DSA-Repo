package com.java.poc.dsa.binary_search;

/**
 * Given a positive integer num, return true if num is a perfect square or false otherwise.
 *
 * A perfect square is an integer that is the square of an integer. In other words, it is the product of some integer with itself.
 *
 * You must not use any built-in library function, such as sqrt.
 *
 *
 *
 * Example 1:
 *
 * Input: num = 16
 * Output: true
 * Explanation: We return true because 4 * 4 = 16 and 4 is an integer.
 * Example 2:
 *
 * Input: num = 14
 * Output: false
 * Explanation: We return false because 3.742 * 3.742 = 14 and 3.742 is not an integer.
 *
 *
 * Constraints:
 *
 * 1 <= num <= 231 - 1
 */
public class PerfectSquare {

    public static void main(String[] args) {
        int num = 808201;
        boolean result = isPerfectSquare(num);
        System.out.println("Is " + num + " a perfect square? " + result);
    }

    public static boolean isPerfectSquare(int num) {
        if (num < 2) {
            return true; // 0 and 1 are perfect squares
        }

        int left = 2;
        int right = num;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            long squared = (long) mid * mid; // Use long to prevent overflow

            if (squared == num) {
                return true; // Found the perfect square
            } else if (squared < num) {
                left = mid + 1; // Try for a larger number
            } else {
                right = mid - 1; // Try for a smaller number
            }
        }

        return false; // No perfect square found
    }
}
