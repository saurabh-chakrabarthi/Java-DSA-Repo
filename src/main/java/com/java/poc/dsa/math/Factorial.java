package com.java.poc.dsa.math;

import java.util.Arrays;

public class Factorial {
    private static int max = 100;
    private static int nil = -1;
    private static int[] lookup = new int[max + 1];

    public static void main(String[] args) {
        int n = 5;
        System.out.println("Factorial of " + n + " is " + fact_tabulation(n));
        initialize(n);
        System.out.println("Factorial of " + n + " is " + fact_memoization(n));
    }

    private static int fact_tabulation(int n) {
        int[] fn = new int[n + 1];
        fn[0] = 1;

        for (int i = 1; i <= n; i++) {
            fn[i] = fn[i - 1] * i;
        }
        System.out.println(Arrays.toString(fn));
        return fn[n];
    }

    private static int fact_memoization(int n) {
        if (n <= 1) {
            return 1;
        } else {
            lookup[n] = n * fact_memoization(n - 1);
        }
        System.out.println(Arrays.toString(lookup));
        return lookup[n];
    }

    private static void initialize(int n) {
        for (int i = 0; i <= n; i++) {
            lookup[i] = nil;
        }
    }
}
