package com.java.poc.dsa.array_and_hashing;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ArrayContainsDuplicates {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 2, 3, 3, 4, 5};
        System.out.println("Contains Dupes : " + containsDuplicate(nums));
        System.out.println("Contains Dupes : " + containsDuplicateUsingSet(nums));
    }

    public static boolean containsDuplicate(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : nums) {
            if (map.containsKey(i)) {
                int val = map.get(i);
                map.put(i, val + 1);
            } else {
                map.put(i, 1);
            }
        }

        for (int j : map.keySet()) {
            if (map.get(j) > 1) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsDuplicateUsingSet(int[] nums) {
        HashSet<Integer> set = new HashSet<Integer>();
        for (int num : nums) {
            if (!set.add(num)) {
                return true;
            }
        }
        return false;
    }
}
