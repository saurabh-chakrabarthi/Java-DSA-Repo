package com.java.poc.common_questions;

public class ReverseString {
    public static void main(String[] args) {
        String str = "Hello World";
        char[] s = str.toCharArray();
        for (int i = 0; i < s.length / 2; i++) {
            char temp = s[i];
            s[i] = s[s.length - i - 1];
            s[s.length - i - 1] = temp;
        }
        System.out.println(new String(s));
    }
}
