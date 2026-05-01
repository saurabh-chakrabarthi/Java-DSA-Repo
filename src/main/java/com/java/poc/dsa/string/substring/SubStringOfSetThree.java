package com.java.poc.dsa.string.substring;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SubStringOfSetThree {

    public static void main(String[] args) {
        String str = "abcabcabc";

        System.out.println("Number of good substrings : "+countGoodSubstrings(str));
    }

    public static int countGoodSubstrings(String s) {
        if(s == null || s.length()<3){
            return 0;
        }
        List<String> subList = new ArrayList<>();
        List<String> goodStr = new ArrayList<>();
        int count = 0;
        Set<Character> set;
        for(int i=0;i<s.length()-2;i++){
            subList.add(s.substring(i,i+3));
        }

        System.out.println(subList);

        for(String str : subList){
            set = new HashSet<>();
            char[] chars = str.toCharArray();

            for (char aChar : chars) {
                set.add(aChar);
            }

            if(set.size() == str.length()){
                count++;
                goodStr.add(str);
            }
        }

        System.out.println(goodStr);
        return count;
    }
}
