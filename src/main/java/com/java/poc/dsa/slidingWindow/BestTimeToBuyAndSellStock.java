package com.java.poc.dsa.slidingWindow;

/**
 * You are given an array prices where prices[i] is the price of a given stock on the ith day.
 *
 * You want to maximize your profit by choosing a single day to buy one stock and choosing a different day in the future to sell that stock.
 *
 * Return the maximum profit you can achieve from this transaction. If you cannot achieve any profit, return 0.
 *
 *
 *
 * Example 1:
 *
 * Input: prices = [7,1,5,3,6,4]
 * Output: 5
 * Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
 * Note that buying on day 2 and selling on day 1 is not allowed because you must buy before you sell.
 * Example 2:
 *
 * Input: prices = [7,6,4,3,1]
 * Output: 0
 * Explanation: In this case, no transactions are done and the max profit = 0.
 *
 *
 * Constraints:
 *
 * 1 <= prices.length <= 105
 * 0 <= prices[i] <= 104
 */
public class BestTimeToBuyAndSellStock {
    public static void main(String[] args) {
        int[] prices = {7,1,5,3,6,4};
        System.out.println(maxProfit(prices));
        System.out.println(maxProfit2(prices));
        System.out.println(maxProfitBruteForce(prices));
    }

    public static int maxProfit(int[] prices) {
        int max = 0;
        int left = 0;
        for(int right=1;right<prices.length;right++){
            if(prices[right]>prices[left]){
                int profit = prices[right]-prices[left];
                max = Math.max(max,profit);
            }else{
                left=right;
            }
        }

        return max;
    }

    public static int maxProfit2(int[] prices) {
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;
        for (int price : prices) {
            if (price < minPrice) {
                minPrice = price;
            } else if (price - minPrice > maxProfit) {
                maxProfit = price - minPrice;
            }
        }
        return maxProfit;
    }
    public static int maxProfitBruteForce(int[] prices) {
        int max = 0;
        for(int left=0;left<prices.length;left++){
            for(int right=left+1;right<prices.length;right++){
                int profit = prices[right]-prices[left];
                max = Math.max(max,profit);
            }
        }

        return max;
    }
}
