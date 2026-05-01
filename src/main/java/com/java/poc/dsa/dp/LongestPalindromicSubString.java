package com.java.poc.dsa.dp;

/**
 * 5. Longest Palindromic Substring
 * Given a string s, return the longest palindromic substring in s.
 * <p>
 * Example 1:
 * Input: s = "babad"
 * Output: "bab"
 * Explanation: "aba" is also a valid answer.
 * <p>
 * Example 2:
 * Input: s = "cbbd"
 * Output: "bb"
 * <p>
 * Constraints:
 * 1 <= s.length <= 1000
 * s consist of only digits and English letters.
 */
public class LongestPalindromicSubString {
    public static void main(String[] args) {
        String s = "racecar";
        System.out.println("Longest palindromic substring in \"" + s + "\" is: " + longestPalindrome(s));
    }

    public static String longestPalindrome(String s) {
        if (s == null || s.isEmpty()){
            return "";
        }

        int start = 0, end = 0;

        for (int i = 0; i < s.length(); i++) {
            int len1 = expandAroundCenter(s, i, i);       // Odd-length palindrome
            int len2 = expandAroundCenter(s, i, i + 1);   // Even-length palindrome
            int len = Math.max(len1, len2);

            end = i + len / 2;
        }

        return s.substring(start, end + 1);
    }

    private static int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }
}