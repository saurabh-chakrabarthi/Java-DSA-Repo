package com.java.poc.dsa.string.sequence_and_subsequence;

import java.util.ArrayList;
import java.util.List;

public class PrintKSumSubsequence {

    public static void main(String[] args) {
        int[] arr = new int[]{1,2,3,4,5};
        int target = 8;
        int sum = 0;
        List<List<Integer>> finalList = new ArrayList<>();

        findSubsets(arr,0,new ArrayList<>(),finalList,target,sum);
        System.out.println(finalList);
    }

    private static void findSubsets(int[] nums,int index,List<Integer> subList,List<List<Integer>> finalList,int target, int sum){

        if(index == nums.length){
            if(sum == target){
                finalList.add(new ArrayList<>(subList));
            }
        }else{
            findSubsets(nums,index+1,subList,finalList,target,sum);
            subList.add(nums[index]);
            sum+=nums[index];
            findSubsets(nums,index+1,subList,finalList,target,sum);
            subList.remove(subList.size()-1);

        }
    }
}
