package com.java.poc.curatedPracticeList.dp_multidimensional;

/**
 * 121. Best Time to Buy and Sell Stock
 * Solved
 * Easy
 * Topics
 * Companies
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
public class StockTradingProfitCalculator {

    public int maxProfit(int[] prices, int fee) {
        if (prices.length <= 1) return 0;
        int days = prices.length;
        int[] buy = new int[days];
        int[] sell = new int[days];
        buy[0] = -prices[0];
        for (int i = 1; i < days; i++) {
            buy[i] = Math.max(buy[i - 1], sell[i - 1] - prices[i]);
            sell[i] = Math.max(sell[i - 1], buy[i - 1] + prices[i] - fee);
        }
        return sell[days - 1];
    }

    public static void main(String[] args) {
        StockTradingProfitCalculator calculator = new StockTradingProfitCalculator();

        assert calculator.maxProfit(new int[]{1,3,2,8,4,9}, 2) == 8 : "Test case 1 failed";
        assert calculator.maxProfit(new int[]{1,3,7,5,10,3}, 3) == 6 : "Test case 2 failed";

        System.out.println("All test cases passed!");
    }
}
