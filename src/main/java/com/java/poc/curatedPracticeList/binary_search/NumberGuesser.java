package com.java.poc.curatedPracticeList.binary_search;

/**
 * 374. Guess Number Higher or Lower
 * Easy
 * Topics
 * Companies
 * We are playing the Guess Game. The game is as follows:
 *
 * I pick a number from 1 to n. You have to guess which number I picked.
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
 */
public class NumberGuesser {

    private int pick;

    public NumberGuesser(int pick) {
        this.pick = pick;
    }

    public int guessNumber(int n) {
        int left = 1, right = n;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int result = guess(mid);
            if (result == 0) {
                return mid;
            } else if (result < 0) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }

    private int guess(int num) {
        return Integer.compare(pick, num);
    }

    public static void main(String[] args) {
        NumberGuesser guesser1 = new NumberGuesser(6);
        assert guesser1.guessNumber(10) == 6 : "Test case 1 failed";

        NumberGuesser guesser2 = new NumberGuesser(1);
        assert guesser2.guessNumber(1) == 1 : "Test case 2 failed";

        NumberGuesser guesser3 = new NumberGuesser(1);
        assert guesser3.guessNumber(2) == 1 : "Test case 3 failed";

        System.out.println("All test cases passed!");
    }
}
