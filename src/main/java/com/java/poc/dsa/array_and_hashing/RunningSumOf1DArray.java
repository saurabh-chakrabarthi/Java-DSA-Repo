package com.java.poc.dsa.array_and_hashing;

import java.util.Arrays;

public class RunningSumOf1DArray {
    public static void main(String[] args) {
        int[] nums = new int[]{1,2,3,4,5};
        System.out.println(Arrays.toString(runningSum(nums)));
    }

    public static int[] runningSum(int[] nums) {
        for(int i = 1 ; i < nums.length ; i++){
            nums[i]=nums[i-1]+nums[i];
        }
        return nums;
    }
}
