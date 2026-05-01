package com.java.poc.dsa.array_and_hashing;

import java.util.*;

public class FormBiggestNumberInArray {

    public static void main(String[] args) {
        int[] nums = new int[]{ 0, 0, 9};

        System.out.println(largestNumber(nums));
    }

    public static String largestNumber(int[] nums) {
        String[] strings = new String[nums.length];
        for(int i = 0; i < nums.length; i++){
            strings[i] = Integer.toString(nums[i]);
        }
        System.out.println(Arrays.toString(strings));
        Arrays.sort(strings,new MyComparator());

        System.out.println(Arrays.toString(strings));
        StringBuilder sb = new StringBuilder();
        if(strings[0].equals("0")){
            return "0";
        }
        for(String str : strings){
            sb.append(str);
        }

        return sb.toString();

    }

    static class MyComparator implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            String xy = o1+o2;
            String yx = o2+o1;

            return yx.compareTo(xy);
        }
    }
}
