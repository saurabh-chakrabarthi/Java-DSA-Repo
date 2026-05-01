package com.java.poc.dsa.array_and_hashing;

import static java.lang.Math.min;

public class Minimum_Steps_To_One {

    public static void main(String[] args) {

        System.out.println(getMinSteps(10));
    }

    private static int getMinSteps(int n) {
        int dp[] = new int[n + 1];
        int i;
        dp[1] = 0;  // trivial case
        for (i = 2; i <= n; i++) {
            dp[i] = 1 + dp[i - 1];
            if (i % 2 == 0) dp[i] = min(dp[i], 1 + dp[i / 2]);
            if (i % 3 == 0) dp[i] = min(dp[i], 1 + dp[i / 3]);
        }
        return dp[n];
    }
}
