package com.java.poc.dsa.string;

import java.util.Arrays;

public class UniqueString {
    public static void main(String[] args) {
        String str = "abcdee";
        System.out.println("is "+str+" unique = "+isUnique(str));
    }

    private static boolean isUnique(String str) {
        char[] chars = str.toCharArray();
        Arrays.sort(chars);
        for (int i = 0; i < chars.length - 1; i++) {
            if (chars[i] == chars[i + 1]) {
                return false;
            }
        }
        return true;
    }
}
