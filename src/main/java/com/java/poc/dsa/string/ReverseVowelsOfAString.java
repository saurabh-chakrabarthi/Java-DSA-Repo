package com.java.poc.dsa.string;

public class ReverseVowelsOfAString {
    public static void main(String[] args) {
        String str = "leetcode";
        System.out.println("reversed vowels : " + reverseVowels(str));
    }

    private static String reverseVowels(String str) {
        int i = 0;
        int j = str.length() - 1;
        String vowels = "aeiouAEIOU";
        char[] chars = str.toCharArray();
        while (i < j) {
            while (i < j && !vowels.contains(chars[i] + "")) {
                i++;
            }
            while (i < j && !vowels.contains(chars[j] + "")) {
                j--;
            }
            char tmp = chars[i];
            chars[i] = chars[j];
            chars[j] = tmp;

            i++;
            j--;

        }
        return new String(chars);
    }
}
