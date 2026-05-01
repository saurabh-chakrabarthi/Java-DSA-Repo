package com.java.poc.curatedPracticeList.blind75;

import java.util.*;

/**
 * 347. Top K Frequent Elements
 * Medium
 * 12.6K
 * 464
 * Companies
 * Given an integer array nums and an integer k, return the k most frequent elements. You may return the answer in any order.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [1,1,1,2,2,3], k = 2
 * Output: [1,2]
 * Example 2:
 *
 * Input: nums = [1], k = 1
 * Output: [1]
 *
 *
 * Constraints:
 *
 * 1 <= nums.length <= 105
 * -104 <= nums[i] <= 104
 * k is in the range [1, the number of unique elements in the array].
 * It is guaranteed that the answer is unique.
 */
public class TopKFrequentElements {

    public static void main(String[] args) {
        int[] nums = new int [] {1,1,2,2,3,2,3,3};
        int k = 2;
        System.out.println("Top frequent elements are : "+Arrays.toString(topKFrequent(nums,k)));
    }

    public static int[] topKFrequent(int[] nums, int k) {
        Map<Integer,Integer> map = new HashMap<>();
        for(int num : nums){
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        // sort list from map.keyset() by map.get(num),
        List<Integer> list = new ArrayList<>(map.keySet());
        Collections.sort(list, (a, b) -> map.get(b) - map.get(a));

        //list.sort((a, b) -> map.get(b) - map.get(a));
        // transfer results from list to int[];
        int[] res = new int[k];
        for (int i = 0; i < k; i++) res[i] = list.get(i);

        return res;
    }

}
