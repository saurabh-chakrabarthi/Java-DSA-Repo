package com.java.poc.dsa.array_and_hashing;

import java.util.Arrays;

public class MoveAllZeroes {
    public static void main(String[] args) {
        int[] nums = new int[]{ 6, 0, 8, 2, 3, 0, 4, 0, 1 };
        rearrangeElements(nums);
    }

    private static void rearrangeElements(int[] nums) {
        System.out.println("Before : "+Arrays.toString(nums));
        int k = 0;
        for(int n : nums){
            if(n != 0){
                nums[k++] = n;
            }
        }
        System.out.println("During : "+Arrays.toString(nums));
        for (int i = k; i < nums.length; i++) {
            nums[i] = 0;
        }
        System.out.println("After : "+Arrays.toString(nums));
    }
}
