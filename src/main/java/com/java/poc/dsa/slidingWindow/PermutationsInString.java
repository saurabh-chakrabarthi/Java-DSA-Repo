package com.java.poc.dsa.slidingWindow;

import java.util.Arrays;
import java.util.Map;

/**
 * Given two strings s1 and s2, return true if s2 contains a permutation of s1, or false otherwise.
 *
 * In other words, return true if one of s1's permutations is the substring of s2.
 *
 *
 *
 * Example 1:
 *
 * Input: s1 = "ab", s2 = "eidbaooo"
 * Output: true
 * Explanation: s2 contains one permutation of s1 ("ba").
 * Example 2:
 *
 * Input: s1 = "ab", s2 = "eidboaoo"
 * Output: false
 *
 *
 * Constraints:
 *
 * 1 <= s1.length, s2.length <= 104
 * s1 and s2 consist of lowercase English letters.
 */
public class PermutationsInString {
    public static void main(String[] args) {
        String s1 = "ab";
        String s2 = "eidbaooo";
        System.out.println(checkInclusion(s1, s2));
        System.out.println(checkInclusionUsingArrays(s1, s2));
    }

    private static boolean checkInclusion(String s1, String s2) {
        if(s1.length() > s2.length()) return false;
        Map<Character, Integer> s1map = new java.util.HashMap<>();
        Map<Character, Integer> s2map = new java.util.HashMap<>();
        for(char c : s1.toCharArray()){
            s1map.put(c, s1map.getOrDefault(c, 0)+1);
        }

        int left = 0;
        for(int right = 0; right<s2.length(); right++){
            char c = s2.charAt(right);
            s2map.put(c, s2map.getOrDefault(c, 0)+1);
            if(right-left+1 > s1.length()){
                char leftChar = s2.charAt(left);
                s2map.put(leftChar, s2map.get(leftChar)-1);
                if(s2map.get(leftChar) == 0){
                    s2map.remove(leftChar);
                }
                left++;
            }
            if(s1map.equals(s2map)){
                return true;
            }
        }
        return false;
    }

    private static boolean checkInclusionUsingArrays(String s1, String s2) {
        if (s1.length() > s2.length()) return false;

        int[] count1 = new int[26];
        int[] count2 = new int[26];

        // Build frequency for s1
        for (char c : s1.toCharArray()) {
            count1[c - 'a']++;
        }

        // First window in s2
        for (int i = 0; i < s1.length(); i++) {
            count2[s2.charAt(i) - 'a']++;
        }

        // Check first window
        if (Arrays.equals(count1, count2)) return true;

        // Slide the window across s2
        for (int i = s1.length(); i < s2.length(); i++) {
            // add new char
            count2[s2.charAt(i) - 'a']++;
            // remove old char
            count2[s2.charAt(i - s1.length()) - 'a']--;

            if (Arrays.equals(count1, count2)) return true;
        }

        return false;
    }
}
