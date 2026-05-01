package com.java.poc.java_basics.collections.map;

import java.util.HashMap;
import java.util.Map;

public class SortAMapByValue {
    public static void main(String[] args) {
        int[] nums = new int[]{1,1,1,2,2,3,3,3,3,4,4,4};
        int k = 2;
        Map<Integer,Integer> map = new HashMap<>();
        for (int n : nums) {
            map.put(n, map.getOrDefault(n, 0) + 1);
        }

        System.out.println("Original Map: " + map);

        int[] result = map.entrySet()
                .stream()
                .sorted((a, b) -> b.getValue() - a.getValue())
                .limit(k)
                .mapToInt(Map.Entry::getKey)
                .toArray();
        System.out.println("Top K Frequent Elements: " + java.util.Arrays.toString(result));
    }
}
