package com.java.poc.common_patterns;

import java.util.ArrayList;
import java.util.List;

public class PalindromicSubStrings {

    public static void main(String[] args) {
        System.out.println(longestPalindrome("aaaba"));
    }

    public static String longestPalindrome(String s) {
        List<String> list = new ArrayList<>();
        for(int i=0;i<s.length();i++){
            for(int j=i+1;j<=s.length();j++){
                if(checkPalindrome(s.substring(i,j))){
                    list.add(s.substring(i,j));
                }
            }
        }

        return list.toString();
    }

    public static boolean checkPalindrome(String s){
        int left = 0;
        int right = s.length()-1;
        while(left<right){
            if(s.charAt(left) != s.charAt(right)){
                return false;
            }else{
                left++;
                right--;
            }
        }

        return true;
    }
}
