package com.java.poc.dsa.string;

public class CheckStringPalindromes {
    public static void main(String[] args) {
        String str = "abcbaa";
        System.out.println("is "+str+" a palindrome ? - "+checkPalindrome(str));
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
