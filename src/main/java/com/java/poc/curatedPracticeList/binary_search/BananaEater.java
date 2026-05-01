package com.java.poc.curatedPracticeList.binary_search;

/**
 * 875. Koko Eating Bananas
 * Medium
 * Topics
 * Companies
 * Koko loves to eat bananas. There are n piles of bananas, the ith pile has piles[i] bananas. The guards have gone and will come back in h hours.
 *
 * Koko can decide her bananas-per-hour eating speed of k. Each hour, she chooses some pile of bananas and eats k bananas from that pile. If the pile has less than k bananas, she eats all of them instead and will not eat any more bananas during this hour.
 *
 * Koko likes to eat slowly but still wants to finish eating all the bananas before the guards return.
 *
 * Return the minimum integer k such that she can eat all the bananas within h hours.
 *
 *
 *
 * Example 1:
 *
 * Input: piles = [3,6,7,11], h = 8
 * Output: 4
 * Example 2:
 *
 * Input: piles = [30,11,23,4,20], h = 5
 * Output: 30
 * Example 3:
 *
 * Input: piles = [30,11,23,4,20], h = 6
 * Output: 23
 *
 *
 * Constraints:
 *
 * 1 <= piles.length <= 104
 * piles.length <= h <= 109
 * 1 <= piles[i] <= 109
 */
public class BananaEater {

    public int minEatingSpeed(int[] piles, int H) {
        int lo = 1, hi = getMaxPile(piles);

        // Binary search to find the smallest valid K.
        while (lo <= hi) {
            int K = lo + ((hi - lo) >> 1);
            if (canEatAll(piles, K, H)) {
                hi = K - 1;
            } else {
                lo = K + 1;
            }
        }

        return lo;
    }

    private boolean canEatAll(int[] piles, int K, int H) {
        int countHour = 0; // Hours take to eat all bananas at speed K.

        for (int pile : piles) {
            countHour += pile / K;
            if (pile % K != 0)
                countHour++;
        }
        return countHour <= H;
    }

    private int getMaxPile(int[] piles) {
        int maxPile = Integer.MIN_VALUE;
        for (int pile : piles) {
            maxPile = Math.max(pile, maxPile);
        }
        return maxPile;
    }

    public static void main(String[] args) {
        BananaEater eater = new BananaEater();

        int[] piles1 = {3, 6, 7, 11};
        int h1 = 8;
        assert eater.minEatingSpeed(piles1, h1) == 4 : "Test case 1 failed";

        int[] piles2 = {30, 11, 23, 4, 20};
        int h2 = 5;
        assert eater.minEatingSpeed(piles2, h2) == 30 : "Test case 2 failed";

        int[] piles3 = {30, 11, 23, 4, 20};
        int h3 = 6;
        assert eater.minEatingSpeed(piles3, h3) == 23 : "Test case 3 failed";

        System.out.println("All test cases passed!");
    }
}
