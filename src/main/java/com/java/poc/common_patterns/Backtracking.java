package com.java.poc.common_patterns;

import java.util.ArrayList;
import java.util.List;

public class Backtracking {

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 5, 8, 90};
        System.out.println(subsets(nums));
    }

    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(result, new ArrayList<>(), nums, 0);
        return result;
    }

    private static void backtrack(List<List<Integer>> result, List<Integer> tempList, int[] nums, int start) {
        result.add(new ArrayList<>(tempList)); // Add current subset

        for (int i = start; i < nums.length; i++) {
            tempList.add(nums[i]); // Choose element
            backtrack(result, tempList, nums, i + 1); // Explore further
            tempList.remove(tempList.size() - 1); // Undo choice (backtrack)
        }
    }

}
