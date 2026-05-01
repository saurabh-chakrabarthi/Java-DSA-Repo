package com.java.poc.dsa.math;

public class Palindrome {
    public static boolean isPalindrome(int x) {
        int sum = 0, target = x;
        while (x > 0) {
            sum = sum * 10 + x % 10;
            x = x / 10;
        }
        return sum == target;
    }

    public static void main(String[] args) {
        int x = 122;
        if (isPalindrome(x)) {
            System.out.println(x + " is a Palindrome !");
        } else {
            System.out.println(x + " is NOT Palindrome !");
        }

    }
}
