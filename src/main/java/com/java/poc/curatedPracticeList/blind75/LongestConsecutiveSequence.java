package com.java.poc.curatedPracticeList.blind75;

import java.util.HashSet;
import java.util.Set;

/**
 * 128. Longest Consecutive Sequence
 * Medium
 * 14.9K
 * 612
 * Companies
 * Given an unsorted array of integers nums, return the length of the longest consecutive elements sequence.
 *
 * You must write an algorithm that runs in O(n) time.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [100,4,200,1,3,2]
 * Output: 4
 * Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.
 * Example 2:
 *
 * Input: nums = [0,3,7,2,5,8,4,6,0,1]
 * Output: 9
 *
 *
 * Constraints:
 *
 * 0 <= nums.length <= 105
 * -109 <= nums[i] <= 109
 */
public class LongestConsecutiveSequence {

    public static void main(String[] args) {
        int[] nums = new int[]{4,0,0,3,2,1,5,6,7,1};
        System.out.println("longest consecutive sequence in the array is : "+longestConsecutive(nums));
    }

    public static int longestConsecutive(int[] nums) {
        if(nums == null || nums.length == 0){
            return 0;
        }
        Set<Integer> set = new HashSet<>();

        for(int i : nums){
            set.add(i);
        }

        int longest=0;
        for(int n : nums){
            if(!set.contains(n-1)){
                int count=1;
                while(set.contains(n+1)){
                    n++;
                    count++;
                }
                longest=Math.max(longest,count);
            }
        }

        return longest;
    }
}
