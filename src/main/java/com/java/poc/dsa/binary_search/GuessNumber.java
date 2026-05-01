package com.java.poc.dsa.binary_search;

/**
 * 374. Guess Number Higher or Lower
 * We are playing the Guess Game. The game is as follows:
 *
 * I pick a number from 1 to n. You have to guess which number I picked (the number I picked stays the same throughout the game).
 *
 * Every time you guess wrong, I will tell you whether the number I picked is higher or lower than your guess.
 *
 * You call a pre-defined API int guess(int num), which returns three possible results:
 *
 * -1: Your guess is higher than the number I picked (i.e. num > pick).
 * 1: Your guess is lower than the number I picked (i.e. num < pick).
 * 0: your guess is equal to the number I picked (i.e. num == pick).
 * Return the number that I picked.
 *
 *
 *
 * Example 1:
 *
 * Input: n = 10, pick = 6
 * Output: 6
 * Example 2:
 *
 * Input: n = 1, pick = 1
 * Output: 1
 * Example 3:
 *
 * Input: n = 2, pick = 1
 * Output: 1
 *
 *
 * Constraints:
 *
 * 1 <= n <= 231 - 1
 * 1 <= pick <= n
 *
 */
public class GuessNumber {
    public static void main(String[] args) {
        int n = 10;
        int pick = 6; // This would be set by the game environment
        int result = guessNumber(n, pick);
        System.out.println("The guessed number is: " + result);
    }
    public static int guessNumber(int n, int pick) {
        int left = 1;
        int right = n;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int res = guess(mid, pick);
            if (res == 0) {
                return mid; // Correct guess
            } else if (res < 0) {
                right = mid - 1; // Guess is higher than the picked number
            } else {
                left = mid + 1; // Guess is lower than the picked number
            }
        }

        return -1; // This line should never be reached if the input is valid
    }

    // Simulated guess API
    private static int guess(int num, int pick) {
        return Integer.compare(pick, num);
    }
}
