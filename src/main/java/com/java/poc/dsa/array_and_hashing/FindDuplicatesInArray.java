package com.java.poc.dsa.array_and_hashing;

import java.util.*;

public class FindDuplicatesInArray {

    public static void main(String[] args) {

        int[] nums = new int[]{1,2,2,3,3,4,5};

        System.out.println(findDupes(nums));
        System.out.println(findDupes2(nums));
    }

    private static List<Integer> findDupes(int[] nums) {
        Map<Integer,Integer> integerMap = new HashMap<>();
        List<Integer> dupArray = new ArrayList<>();
        for (int num : nums) {
            if (integerMap.containsKey(num)) {
                integerMap.put(num, integerMap.get(num)+1);
            } else {
                integerMap.put(num, 1);
            }
        }

        for(int key : integerMap.keySet()){
            if(integerMap.get(key) > 1){
                dupArray.add(key);
            }
        }


        return dupArray;
    }


    private static List<Integer> findDupes2(int[] nums) {
        Set<Integer> set = new HashSet<>();
        List<Integer> dupArray = new ArrayList<>();
        for (int num : nums) {
            if (set.contains(num)) {
                dupArray.add(num);
            } else {
                set.add(num);
            }
        }

        return dupArray;
    }
}
