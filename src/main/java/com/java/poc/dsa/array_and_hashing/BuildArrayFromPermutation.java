package com.java.poc.dsa.array_and_hashing;

import java.util.Arrays;

public class BuildArrayFromPermutation {

    public static void main(String[] args) {
        int[] nums = new int[]{1,4,2,3,5,7,6,3};
        System.out.println(Arrays.toString(buildArray(nums)));
    }

    public static int[] buildArray(int[] nums) {
        int[] result = new int[nums.length];

        for(int i : nums){
            result[i] = nums[nums[i]];
        }


        return result;
    }
}
