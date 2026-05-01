package com.java.poc.dsa.dp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Same as Coin Change problem but it returns the combination of coins as final answer
 */
public class CoinChangeCombinations {
    public static void main(String[] args) {
        int[] coins = {1, 2, 5};
        int amount = 11;
        System.out.println("Combinations of coins to make up " + amount + " are: "+coinChangeCombination(coins, amount));

    }

    public static List<Integer> coinChangeCombination(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        int[] prevCoin = new int[amount + 1];

        Arrays.fill(dp, amount + 1);
        Arrays.fill(prevCoin, -1);  // no coin used yet
        dp[0] = 0;

        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (coin <= i && dp[i - coin] + 1 < dp[i]) {
                    dp[i] = dp[i - coin] + 1;
                    prevCoin[i] = coin;
                }
            }
        }

        // If amount is not reachable
        if (dp[amount] > amount) {
            return Collections.emptyList();
        }

        // Reconstruct the coin combination
        List<Integer> result = new ArrayList<>();
        int curr = amount;
        while (curr > 0) {
            int coin = prevCoin[curr];
            result.add(coin);
            curr -= coin;
        }

        return result;  // list of coins used
    }
}
