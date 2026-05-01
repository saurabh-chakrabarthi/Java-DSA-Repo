package com.java.poc.dsa.string;

public class FindAndRemoveDuplicates {
    public static void main(String[] args) {
        String str = "abbaca";
        System.out.println("String after removing duplicates: " + removeDuplicates(str));
    }

    private static String removeDuplicates(String str) {
        StringBuilder sb = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (!sb.isEmpty() && sb.charAt(sb.length() - 1) == c) {
                sb.deleteCharAt(sb.length() - 1);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
