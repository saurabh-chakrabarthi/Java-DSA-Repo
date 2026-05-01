package com.java.poc.dsa.array_and_hashing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MajorityElement2 {

    public static void main(String[] args) {
        int[] nums = new int[] {2,3,3};
        System.out.println("Majority element is : "+majorityElement(nums));
    }

    public static List<Integer> majorityElement(int[] nums) {
        List<Integer> list = new ArrayList<>();
        Map<Integer, Integer> myMap = new HashMap<Integer, Integer>();
        for (int num: nums) {
            if (!myMap.containsKey(num)){
                myMap.put(num, 1);
            }else{
                myMap.put(num, myMap.get(num)+1);
            }
        }

        for(Integer key : myMap.keySet()){
            if(myMap.get(key)>nums.length/3){
                list.add(key);
            }
        }

        return list;
    }
}
