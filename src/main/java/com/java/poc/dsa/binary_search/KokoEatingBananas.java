package com.java.poc.dsa.binary_search;

/**
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
public class KokoEatingBananas {
    public static void main(String[] args) {
        int[] piles = {30,11,23,4,20};
        int h = 5;
        System.out.println(minEatingSpeed(piles, h));
        System.out.println(bruteForce(piles, h));
    }

    public static int minEatingSpeed(int[] piles, int h) {
        int max = Integer.MIN_VALUE;
        for(int pile : piles){
            max = Math.max(pile,max);
        }

        int left = 1;
        int right = max;
        while(left<=right){
            int mid = left + (right-left)/2;
            long hours = 0;
            for(int pile : piles){
                hours += (long) Math.ceil((double) pile / mid);
            }
            if(hours<=h){
                right = mid-1;
            }else{
                left = mid+1;
            }
        }

        return left;
    }

    public static int bruteForce(int[] piles, int h) {
        int max = Integer.MIN_VALUE;
        for(int pile : piles){
            max = Math.max(max, pile);
        }

        for(int speed=1; speed<=max; speed++){
            long hours = 0;
            for(int pile : piles){
                hours += (long) Math.ceil((double)pile/speed);
            }
            if(hours <= h){
                return speed;
            }
        }

        return max;
    }
}
