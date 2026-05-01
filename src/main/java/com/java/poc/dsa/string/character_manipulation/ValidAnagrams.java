package com.java.poc.dsa.string.character_manipulation;

import java.util.Arrays;

public class ValidAnagrams {
    public static void main(String[] args) {
        String s = "anagram";
        String t = "margana";

        System.out.println("is anagram valid ? : "+isAnagramValid(s,t));
        System.out.println("is anagram valid ? : "+isAnagram(s,t));
    }

    public static boolean isAnagramValid(String s, String t) {
        char[] c1 = s.toCharArray();
        char[] c2 = t.toCharArray();
        Arrays.sort(c1);
        Arrays.sort(c2);
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        System.out.println(Arrays.toString(c1));
        System.out.println(Arrays.toString(c2));
        for(Character c : c1){
            sb1.append(c);
        }
        for(Character c : c2){
            sb2.append(c);
        }
        System.out.println(sb1);
        System.out.println(sb2);
        return sb1.toString().equals(sb2.toString());
    }

    public static boolean isAnagram(String s, String t) {
        int[] a = new int[26];
        for (char c : s.toCharArray()) {
            a[c - 'a']++;
        }
        for (char c : t.toCharArray()) {
            a[c - 'a']--;
        }
        for (int n : a) {
            if (n != 0) return false;
        }
        return true;
    }
}
