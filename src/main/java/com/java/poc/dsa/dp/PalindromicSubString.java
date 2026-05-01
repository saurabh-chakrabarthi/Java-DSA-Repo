package com.java.poc.dsa.dp;

import java.util.Objects;

/**
 * 647. Palindromic Substrings
 * <p>
 * Given a string s, return the number of palindromic substrings in it.
 * <p>
 * A string is a palindrome when it reads the same backward as forward.
 * <p>
 * A substring is a contiguous sequence of characters within the string.
 * <p>
 * <p>
 * <p>
 * Example 1:
 * <p>
 * Input: s = "abc"
 * Output: 3
 * Explanation: Three palindromic strings: "a", "b", "c".
 * Example 2:
 * <p>
 * Input: s = "aaa"
 * Output: 6
 * Explanation: Six palindromic strings: "a", "a", "a", "aa", "aa", "aaa".
 * <p>
 * <p>
 * Constraints:
 * <p>
 * 1 <= s.length <= 1000
 * s consists of lowercase English letters.
 */
public class PalindromicSubString {
    public static void main(String[] args) {
        String s = "racecar";
        System.out.println("Number of palindromic substrings in \"" + s + "\": " + countSubstrings(s));
    }

    public static int countSubstrings(String s) {
        if (s == null || s.isEmpty()){
            return 0;
        }

        int count = 0;

        for (int i = 0; i < s.length(); i++) {
            count += expandAroundCenter(s, i, i);       // Odd-length palindromes
            count += expandAroundCenter(s, i, i + 1);   // Even-length palindromes
        }

        return count;
    }

    private static int expandAroundCenter(String s, int left, int right) {
        int count = 0;

        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            count++;  // Count the current palindrome
            left--;
            right++;
        }

        return count;
    }
}
