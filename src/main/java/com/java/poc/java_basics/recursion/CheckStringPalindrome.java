package com.java.poc.java_basics.recursion;

import java.util.Arrays;

public class CheckStringPalindrome {

    public static void main(String[] args) {
        String str = "abcba";
        System.out.println("Is Palindrome : "+isPalindrome(str));
    }

    public static boolean isPalindrome(String str) {
        if (str.length() <= 1) {
            return true;
        }
        return str.charAt(0) == str.charAt(str.length() - 1) && isPalindrome(str.substring(1, str.length() - 1));
    }
}
