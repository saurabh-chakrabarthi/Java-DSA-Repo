package com.java.poc.dsa.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayPermutations {
    public static void main(String[] args) {
        List<List<Integer>> permutationList = new ArrayList<>();
        int[] nums = new int[]{1, 2, 3};
        Arrays.sort(nums);
        permute(nums, permutationList, new ArrayList<Integer>());
        System.out.println(permutationList);
    }

    public static void permute(int[] nums, List<List<Integer>> permutationList, ArrayList<Integer> tmpList) {

        if (tmpList.size() == nums.length) {
            permutationList.add(new ArrayList<>(tmpList));
        }
        for (int num : nums) {
            if (!tmpList.contains(num)) {
                tmpList.add(num);
                permute(nums, permutationList, tmpList);
                tmpList.remove(tmpList.size() - 1);
            }
        }
    }
}
