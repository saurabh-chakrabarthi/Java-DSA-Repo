package com.java.poc.dsa.string.sequence_and_subsequence;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 90. Subsets II
 * Medium
 * 8.7K
 * 245
 * Companies
 * Given an integer array nums that may contain duplicates, return all possible
 * subsets
 *  (the power set).
 *
 * The solution set must not contain duplicate subsets. Return the solution in any order.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [1,2,2]
 * Output: [[],[1],[1,2],[1,2,2],[2],[2,2]]
 * Example 2:
 *
 * Input: nums = [0]
 * Output: [[],[0]]
 *
 *
 * Constraints:
 *
 * 1 <= nums.length <= 10
 * -10 <= nums[i] <= 10
 */
public class PrintUniqueSubsequence {
    public static void main(String[] args) {
        int[] arr = new int[]{1,2,2};
        Set<List<Integer>> set = new HashSet<>();
        findSubsets(arr,0,new ArrayList<>(), set);
        System.out.println(new ArrayList<>(set));
    }

    private static void findSubsets(int[] nums, int index, List<Integer> subList, Set<List<Integer>> set) {
        if(index == nums.length){
            set.add(subList);
        }else{
            List<Integer> subList1 = new ArrayList<>();
            findSubsets(nums,index+1,subList1, set);
            subList1.add(nums[index]);
            findSubsets(nums,index+1,subList1, set);
            subList1.remove(subList1.size()-1);

        }
    }
}
