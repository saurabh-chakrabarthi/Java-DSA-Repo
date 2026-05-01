package com.java.poc.dsa.binary_search;

/**
 * You have n coins and you want to build a staircase with these coins. The staircase consists of k rows where the ith row has exactly i coins. The last row of the staircase may be incomplete.
 *
 * Given the integer n, return the number of complete rows of the staircase you will build.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: n = 5
 * Output: 2
 * Explanation: Because the 3rd row is incomplete, we return 2.
 * Example 2:
 *
 *
 * Input: n = 8
 * Output: 3
 * Explanation: Because the 4th row is incomplete, we return 3.
 *
 *
 * Constraints:
 *
 * 1 <= n <= 231 - 1
 */
public class ArrangingCoins {
    public static void main(String[] args) {
        int n = 8;
        int result = arrangeCoinsBruteForce(n);
        System.out.println("The number of complete rows is: " + result);

        int resultBinarySearch = arrangeCoinsBinarySearch(n);
        System.out.println("The number of complete rows using Binary Search is: " + resultBinarySearch);
    }

    public static int arrangeCoinsBruteForce(int n) {
        int row = 0;
        while(row+1<=n){
            row++;
            n-=row;
        }
        return row;
    }

    public static int arrangeCoinsBinarySearch(int n) {
        int left = 1;
        int right = n;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            long coinsNeeded = (long) mid * (mid + 1) / 2; // Use long to prevent overflow

            if (coinsNeeded == n) {
                return mid; // Found exact match
            } else if (coinsNeeded < n) {
                left = mid + 1; // Try for a larger row
            } else {
                right = mid - 1; // Try for a smaller row
            }
        }

        return right; // right is the largest k where k(k+1)/2 <= n
    }
}
