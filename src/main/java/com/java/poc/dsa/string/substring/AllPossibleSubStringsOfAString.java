package com.java.poc.dsa.string.substring;

import java.util.ArrayList;
import java.util.List;

public class AllPossibleSubStringsOfAString {

    public static void main(String[] args) {
        String str = "ABC";
        printPermutations(str);
    }

    private static void printPermutations(String str) {
        List<String> strList = new ArrayList<>();
        for(int i = 0; i< str.length(); i++){
            for(int j = i+1; j <= str.length();j++){
                strList.add(str.substring(i,j));
            }
        }
        System.out.println(strList);
    }
}
