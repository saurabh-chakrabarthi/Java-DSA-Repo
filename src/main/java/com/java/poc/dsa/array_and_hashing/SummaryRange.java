package com.java.poc.dsa.array_and_hashing;

import java.util.ArrayList;
import java.util.List;

/**
 * Leetcode 228
 *
 You are given a sorted unique integer array nums.

 A range [a,b] is the set of all integers from a to b (inclusive).

 Return the smallest sorted list of ranges that cover all the numbers in the array exactly. That is, each element of nums is covered by exactly one of the ranges, and there is no integer x such that x is in one of the ranges but not in nums.

 Each range [a,b] in the list should be output as:

 "a->b" if a != b
 "a" if a == b
 */
public class SummaryRange {

    public static void main(String[] args) {
        int[] nums = new int[]{1,2,3,4,21,22,23,24,56};
        System.out.println(summaryRanges(nums));
    }

    public static List<String> summaryRanges(int[] nums) {
        int length = nums.length;
        List<String> result = new ArrayList<String>(length);
        for (int i = 0; i < length; i++) {
            int num = nums[i];
            while (i < length - 1 && nums[i] + 1 == nums[i + 1]) {
                i++;
            }
            if (num != nums[i]) {
                result.add(num + "->" + nums[i]);
            } else {
                result.add(num + "");
            }
        }
        return result;
    }
}
