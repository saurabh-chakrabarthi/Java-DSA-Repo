package com.java.poc.curatedPracticeList.dp_1d;

/**
 * 746. Min Cost Climbing Stairs
 * Easy
 * Topics
 * Companies
 * Hint
 * You are given an integer array cost where cost[i] is the cost of ith step on a staircase. Once you pay the cost, you can either climb one or two steps.
 *
 * You can either start from the step with index 0, or the step with index 1.
 *
 * Return the minimum cost to reach the top of the floor.
 *
 *
 *
 * Example 1:
 *
 * Input: cost = [10,15,20]
 * Output: 15
 * Explanation: You will start at index 1.
 * - Pay 15 and climb two steps to reach the top.
 * The total cost is 15.
 * Example 2:
 *
 * Input: cost = [1,100,1,1,1,100,1,1,100,1]
 * Output: 6
 * Explanation: You will start at index 0.
 * - Pay 1 and climb two steps to reach index 2.
 * - Pay 1 and climb two steps to reach index 4.
 * - Pay 1 and climb two steps to reach index 6.
 * - Pay 1 and climb one step to reach index 7.
 * - Pay 1 and climb two steps to reach index 9.
 * - Pay 1 and climb one step to reach the top.
 * The total cost is 6.
 *
 *
 * Constraints:
 *
 * 2 <= cost.length <= 1000
 * 0 <= cost[i] <= 999
 */
public class StairCostCalculator {

    private int[] dp;

    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        dp = new int[n];
        return Math.min(minCost(cost, n - 1), minCost(cost, n - 2));
    }

    private int minCost(int[] cost, int n) {
        if (n < 0) return 0;
        if (n == 0 || n == 1) return cost[n];
        if (dp[n] != 0) return dp[n];
        dp[n] = cost[n] + Math.min(minCost(cost, n - 1), minCost(cost, n - 2));
        return dp[n];
    }

    public static void main(String[] args) {
        StairCostCalculator calculator = new StairCostCalculator();

        int[] cost1 = {10, 15, 20};
        assert calculator.minCostClimbingStairs(cost1) == 15 : "Test case 1 failed";

        int[] cost2 = {1, 100, 1, 1, 1, 100, 1, 1, 100, 1};
        assert calculator.minCostClimbingStairs(cost2) == 6 : "Test case 2 failed";

        System.out.println("All test cases passed!");
    }
}
