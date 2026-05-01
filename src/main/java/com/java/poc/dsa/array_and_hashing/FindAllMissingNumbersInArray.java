package com.java.poc.dsa.array_and_hashing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 448. Find All Numbers Disappeared in an Array
 * Easy
 * 7.9K
 * 425
 * Companies
 * Given an array nums of n integers where nums[i] is in the range [1, n], return an array of all the integers in the range [1, n] that do not appear in nums.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [4,3,2,7,8,2,3,1]
 * Output: [5,6]
 * Example 2:
 *
 * Input: nums = [1,1]
 * Output: [2]
 *
 *
 * Constraints:
 *
 * n == nums.length
 * 1 <= n <= 105
 * 1 <= nums[i] <= n
 *
 *
 * Follow up: Could you do it without extra space and in O(n) runtime? You may assume the returned list does not count as extra space.
 */
public class FindAllMissingNumbersInArray {
    public static void main(String[] args) {
        int[] nums = {2,0,1,1,3,4,5};
        System.out.println(findDisappearedNumbers(nums));
    }

    public static List<Integer> findDisappearedNumbers(int[] nums) {
        Set<Integer> set = new HashSet<>();
        ArrayList<Integer> result = new ArrayList<>();

        for (int i : nums){
            set.add(i); // removing duplicate elements
        }

        for(int i = 1; i <= nums.length; i++){
            if(!set.contains(i)) result.add(i);
        }
        return result;
    }
}
