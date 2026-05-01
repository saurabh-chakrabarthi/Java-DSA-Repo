package com.java.poc.dsa.string.sequence_and_subsequence;

import java.util.ArrayList;
import java.util.List;

public class PrintAnyOneSumSubsequence {

    public static void main(String[] args) {
        int[] arr = new int[]{1,2,3,4,5};
        int target = 8;
        int sum = 0;
        int count = 0;
        List<List<Integer>> finalList = new ArrayList<>();

        findSubsets(arr,0,new ArrayList<>(),finalList,target,sum,count);
        System.out.println(finalList);
    }

    private static void findSubsets(int[] nums,int index,List<Integer> subList,List<List<Integer>> finalList,int target, int sum, int count){

        if(index == nums.length){
            if(sum == target && count == 0){
                finalList.add(new ArrayList<>(subList));
                count++;
            }
        }else{
            findSubsets(nums,index+1,subList,finalList,target,sum,count);
            subList.add(nums[index]);
            sum+=nums[index];
            findSubsets(nums,index+1,subList,finalList,target,sum,count);
            subList.remove(subList.size()-1);

        }
    }
}
