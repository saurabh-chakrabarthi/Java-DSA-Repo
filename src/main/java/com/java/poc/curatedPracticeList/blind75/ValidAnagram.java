package com.java.poc.curatedPracticeList.blind75;

import java.util.Arrays;

/**
 * 242. Valid Anagram
 * Solved
 * Easy
 * Topics
 * Companies
 * Given two strings s and t, return true if t is an anagram of s, and false otherwise.
 *
 * An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase, typically using all the original letters exactly once.
 *
 *
 *
 * Example 1:
 *
 * Input: s = "anagram", t = "nagaram"
 * Output: true
 * Example 2:
 *
 * Input: s = "rat", t = "car"
 * Output: false
 *
 *
 * Constraints:
 *
 * 1 <= s.length, t.length <= 5 * 104
 * s and t consist of lowercase English letters.
 *
 *
 * Follow up: What if the inputs contain Unicode characters? How would you adapt your solution to such a case?
 */
public class ValidAnagram {

    public static void main(String[] args) {
        System.out.println(isAnagram1("rat","Tar"));
        System.out.println(isAnagram2("rat","Tar"));
        System.out.println(isAnagram3("rat","Tar"));
    }

    public static boolean isAnagram1(String s, String t) {
        char[] chars_s = s.toCharArray();
        Arrays.sort(chars_s);
        StringBuilder sb1 = new StringBuilder();
        for(Character c1 : chars_s){
            sb1.append(c1.toString());
        }

        char[] chars_t = t.toCharArray();
        Arrays.sort(chars_t);
        StringBuilder sb2 = new StringBuilder();
        for(Character c2 : chars_t){
            sb2.append(c2.toString());
        }

        return sb1.toString().equals(sb2.toString());
    }

    public static boolean isAnagram2(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        int[] counts = new int[26]; // Assuming only lowercase letters

        for (char c : s.toLowerCase().toCharArray()) {
            counts[c - 'a']++;
        }

        for (char c : t.toLowerCase().toCharArray()) {
            counts[c - 'a']--;
            if (counts[c - 'a'] < 0) {
                return false; // Character count mismatch
            }
        }

        return true;
    }

    public static boolean isAnagram3(String s, String t) {
        char[] chars_s = s.toCharArray();
        Arrays.sort(chars_s);

        char[] chars_t = t.toCharArray();
        Arrays.sort(chars_t);

        for(int i=0;i<s.length();i++){
            if(chars_s[i] != chars_t[i]){
                return false;
            }
        }

        return true;
    }

}
