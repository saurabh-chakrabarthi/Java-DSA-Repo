package com.java.poc.dsa.array_and_hashing;

import java.util.Arrays;

public class ShuffleTheArray {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 4};
        System.out.println(Arrays.toString(shuffleArray(nums, 2)));
    }

    private static int[] shuffleArray(int[] nums, int n) {
        int left = 0;
        int mid = n;
        int[] result = new int[nums.length];
        for (int i = 0; i < 2*n; i=i+2) {
            result[i] = nums[left++];
            result[i+1] = nums[mid++];
        }
        return result;
    }
}
