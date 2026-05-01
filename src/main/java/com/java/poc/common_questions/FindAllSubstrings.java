package com.java.poc.common_questions;

public class FindAllSubstrings {

    public static void main(String[] args) {
        String str = "abc";
        for (int i = 0; i < str.length(); i++) {
            for (int j = i + 1; j <= str.length(); j++) {
                System.out.println(str.substring(i, j));
            }
        }
    }
}
