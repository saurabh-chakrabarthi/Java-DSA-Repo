package com.java.poc.common_questions;

public class CheckPalindrome {
    public static void main(String[] args) {
       String str = "12321";
       System.out.println("Is " + str + " a palindrome? " + checkPalindrome(str));
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
