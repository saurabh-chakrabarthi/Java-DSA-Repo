package com.java.poc.dsa.string;

/**
 * 680. Valid Palindrome II
 * Easy
 * 6.8K
 * 351
 * Companies
 * Given a string s, return true if the s can be palindrome after deleting at most one character from it.
 *
 *
 *
 * Example 1:
 *
 * Input: s = "aba"
 * Output: true
 * Example 2:
 *
 * Input: s = "abca"
 * Output: true
 * Explanation: You could delete the character 'c'.
 * Example 3:
 *
 * Input: s = "abc"
 * Output: false
 *
 *
 * Constraints:
 *
 * 1 <= s.length <= 105
 * s consists of lowercase English letters.
 */
public class ValidPalindrome2 {
    public static void main(String[] args) {
        String s = "abcba";
        System.out.println("Is Valid palindrome ? "+validPalindrome(s));
    }

    public static boolean validPalindrome(String s) {
        int left = 0;
        int right = s.length()-1;

        while(left < right){
            if(s.charAt(left) != s.charAt(right)){
                return helper(s,left+1,right) || helper(s,left,right-1);
            }

            left++;
            right--;
        }

        return true;
    }

    public static boolean helper(String s, int left, int right){
        int i = left;
        int j = right;

        while(i < j){
            if(s.charAt(i) != s.charAt(j)){
                return false;
            }

            i++;
            j--;
        }

        return true;
    }
}
