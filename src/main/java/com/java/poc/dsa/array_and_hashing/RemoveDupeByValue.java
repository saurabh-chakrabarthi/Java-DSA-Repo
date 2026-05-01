package com.java.poc.dsa.array_and_hashing;

import java.util.Arrays;

public class RemoveDupeByValue {

    public static void main(String[] args) {
        int[] nums = new int[]{3, 4, 1, 2, 4, 5, 1, 2, 5, 2, 3, 5};
        removeElement(nums, 3);
    }


    public static void removeElement(int[] nums, int val) {
        Arrays.sort(nums);
        if (nums.length > 2) {
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] == val) {
                    nums[i] = nums[nums.length - 1 - i];
                    i++;
                }

            }
            System.out.println(Arrays.toString(nums));
        }
    }
}
