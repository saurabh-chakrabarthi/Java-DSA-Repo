package com.java.poc.dsa.array_and_hashing;

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
        System.out.println("Top frequent elements using priority queue are : "+Arrays.toString(topKFrequentUsingPriorityQueue(nums,k)));
        System.out.println("Top frequent elements using bucket sort are : "+Arrays.toString(topKFrequentUsingBucketSort(nums,k)));
        System.out.println("Top frequent elements using stream are : "+Arrays.toString(topKFrequentUsingStream(nums,k)));
    }

    public static int[] topKFrequent(int[] nums, int k) {
        Map<Integer,Integer> map = new HashMap<>();
        int[] result = new int[k];
        for(int n : nums){
            map.put(n,map.getOrDefault(n,0)+1);
        }

        List<Integer> list = new ArrayList<>(map.keySet());
        list.sort((a,b)->map.get(b)-map.get(a));
        for(int i=0;i<k;i++){
            result[i] = list.get(i);
        }

        return result;
    }

    public static int[] topKFrequentUsingPriorityQueue(int[] nums, int k) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : nums) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }

        PriorityQueue<Map.Entry<Integer, Integer>> minHeap = new PriorityQueue<>(
                Comparator.comparingInt(Map.Entry::getValue)
        );

        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            minHeap.offer(entry);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }

        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = minHeap.poll().getKey();
        }
        return result;
    }

    public static int[] topKFrequentUsingBucketSort(int[] nums, int k) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : nums) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }

        List<Integer>[] buckets = new List[nums.length + 1];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new ArrayList<>();
        }

        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            int freq = entry.getValue();
            buckets[freq].add(entry.getKey());
        }

        int[] result = new int[k];
        int index = 0;
        for (int i = buckets.length - 1; i >= 0 && index < k; i--) {
            for (int num : buckets[i]) {
                if (index < k) {
                    result[index++] = num;
                } else {
                    break;
                }
            }
        }
        return result;
    }

    public static int[] topKFrequentUsingStream(int[] nums, int k) {
        Map<Integer,Integer> map = new HashMap<>();
        for (int n : nums) {
            map.put(n, map.getOrDefault(n, 0) + 1);
        }

        int[] result = map.entrySet()
                .stream()
                .sorted((a, b) -> b.getValue() - a.getValue())
                .limit(k)
                .mapToInt(Map.Entry::getKey)
                .toArray();

        return result;
    }

}
