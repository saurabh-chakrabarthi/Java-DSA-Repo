package com.java.poc.dsa.array_and_hashing;

import java.util.Arrays;

public class RemoveElement {

    public static void main(String[] args) {
        int[] nums = new int[]{3,2,2,3};
        int target = 3;
        System.out.println("Original Array : "+ Arrays.toString(nums));
        removeElement(nums,target);

    }

    public static int removeElement(int[] nums, int val) {
        int c = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                nums[c] = nums[i];
                c++;
            }
        }
        System.out.println("Modified Array : "+ Arrays.toString(nums));
        return c;
    }
}
