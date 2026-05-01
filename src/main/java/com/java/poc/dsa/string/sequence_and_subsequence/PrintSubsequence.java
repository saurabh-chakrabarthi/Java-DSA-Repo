package com.java.poc.dsa.string.sequence_and_subsequence;

import java.util.*;

/**
 *
 * 78. Subsets
 * Medium
 * 15.3K
 * 223
 * Companies
 * Given an integer array nums of unique elements, return all possible
 * subsets
 *  (the power set).
 *
 * The solution set must not contain duplicate subsets. Return the solution in any order.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [1,2,3]
 * Output: [[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
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
 * All the numbers of nums are unique.
 */
public class PrintSubsequence {

    public static void main(String[] args) {
        int[] arr = new int[]{1,2,2};
        List<List<Integer>> finalList = new ArrayList<>();
        findSubsets(arr,0,new ArrayList<>(),finalList);
        System.out.println(finalList);
    }

    private static void findSubsets(int[] nums, int index, List<Integer> subList,List<List<Integer>> finalList) {
        if(index == nums.length){
            finalList.add(new ArrayList<>(subList));
        }else{
            findSubsets(nums,index+1,subList,finalList);
            subList.add(nums[index]);
            findSubsets(nums,index+1,subList,finalList);
            subList.remove(subList.size()-1);

        }
    }
}
