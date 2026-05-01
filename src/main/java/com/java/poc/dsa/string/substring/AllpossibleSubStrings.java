package com.java.poc.dsa.string.substring;

import java.util.ArrayList;
import java.util.List;

public class AllpossibleSubStrings {

    public static void main(String[] args) {
        String str = "abbc";
        List<String> strList = new ArrayList<>();
        getSubStrings(str,strList);
        System.out.println(strList);
    }

    private static void getSubStrings(String str, List<String> strList){

        int length = str.length();

        for(int i=0;i<length;i++){
            for(int j=i+1;j<=length;j++){
                strList.add(str.substring(i,j));
            }
        }

    }
}
