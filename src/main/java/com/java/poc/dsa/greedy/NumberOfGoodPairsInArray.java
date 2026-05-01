package com.java.poc.dsa.greedy;

import java.util.HashMap;
import java.util.Map;

public class NumberOfGoodPairsInArray {
    public static void main(String[] args) {
        int[] nums = new int[]{1,1,1,1};
        System.out.println("Number of identical pairs are : " + numIdenticalPairs(nums));
        System.out.println("Number of identical pairs are : " + numIdenticalPairsUsingHashmap(nums));
    }

    public static int numIdenticalPairs(int[] nums) {
        int count = 0;
        for(int i=0;i<nums.length;i++) {
            for (int j = i+1; j <nums.length; j++) {
                if (nums[i] == nums[j]) {
                    count++;
                }
            }
        }
        return count;
    }

    public static int numIdenticalPairsUsingHashmap(int[] nums) {
        Map<Integer,Integer> map = new HashMap<>();
        int count = 0;
        for(int i=0;i<nums.length;i++) {
            if(map.containsKey(nums[i])){
                int val = map.get(nums[i]);
                count +=val;
                map.put(nums[i],val+1);
            }else {
                map.put(nums[i],1);
            }
        }
        return count;
    }
}
