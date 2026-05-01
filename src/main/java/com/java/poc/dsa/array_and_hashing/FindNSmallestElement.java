package com.java.poc.dsa.array_and_hashing;

import java.util.HashSet;
import java.util.Set;

public class FindNSmallestElement {
    public static void main(String[] args) {
        int[] nums = new int[]{12, 13, 1, 10, 34, 1};
        findElement(nums,2);
    }

    private static void findElement(int[] nums, int n) {
        Set<Integer> numSet = new HashSet<>();
        int[] newNums = new int[nums.length];
        for(int i : nums) {
            numSet.add(i);
        }

    }
}
