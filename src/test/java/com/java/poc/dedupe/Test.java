package com.java.poc.dedupe;

class Test {
    public int maxProduct(int[] nums) {
        int current_product = Integer.MIN_VALUE;
        int max_product = Integer.MIN_VALUE;
        int n = nums.length;

        if (n == 0) {
            return max_product;
        } else if (n == 1) {
            return nums[0];
        } else {
            for (int i = 0; i < n; i++) {
                current_product = nums[i];
                for (int j = i + 1; j < n; j++) {
                    current_product = current_product * nums[j];
                    if (current_product > max_product) {
                        max_product = current_product;
                    }
                }

            }
        }

        return max_product;
    }
}