package com.java.poc.dsa.string.substring;

/**
 * 647. Palindromic Substrings
 * Solved
 * Medium
 * Topics
 * Companies
 * Hint
 * Given a string s, return the number of palindromic substrings in it.
 *
 * A string is a palindrome when it reads the same backward as forward.
 *
 * A substring is a contiguous sequence of characters within the string.
 *
 *
 *
 * Example 1:
 *
 * Input: s = "abc"
 * Output: 3
 * Explanation: Three palindromic strings: "a", "b", "c".
 * Example 2:
 *
 * Input: s = "aaa"
 * Output: 6
 * Explanation: Six palindromic strings: "a", "a", "a", "aa", "aa", "aaa".
 *
 *
 * Constraints:
 *
 * 1 <= s.length <= 1000
 * s consists of lowercase English letters.
 */
public class PalindromicSubStrings {

    public static void main(String[] args) {
        System.out.println("Total count of palindromic substrings : "+countSubstrings("aaaba"));
    }
    public static int countSubstrings(String s) {
        int count = 0;
        for(int i = 0; i< s.length(); i++){
            for(int j = i+1; j <= s.length();j++){
                String tmp = s.substring(i,j);
                if(checkPalindrome(tmp)){
                    System.out.println("palindromic substring : "+tmp);
                    count ++;
                }
            }
        }
        return count;
    }

    private static boolean checkPalindrome(String str) {
        int left = 0;
        int right = str.length() - 1;
        while (left <= right) {
            if (str.charAt(left) != str.charAt(right)) {
                return false;
            } else {
                left++;
                right--;
            }
        }
        return true;
    }
}

