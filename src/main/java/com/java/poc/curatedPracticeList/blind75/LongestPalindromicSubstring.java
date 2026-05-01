package com.java.poc.curatedPracticeList.blind75;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 5. Longest Palindromic Substring
 * Solved
 * Medium
 * Topics
 * Companies
 * Hint
 * Given a string s, return the longest
 * palindromic
 *
 * substring
 *  in s.
 *
 *
 *
 * Example 1:
 *
 * Input: s = "babad"
 * Output: "bab"
 * Explanation: "aba" is also a valid answer.
 * Example 2:
 *
 * Input: s = "cbbd"
 * Output: "bb"
 *
 *
 * Constraints:
 *
 * 1 <= s.length <= 1000
 * s consist of only digits and English letters.
 */
public class LongestPalindromicSubstring {

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

        list.sort(Comparator.comparing(String::length));
        return list.get(list.size()-1);
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
