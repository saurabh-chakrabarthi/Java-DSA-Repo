package com.java.poc.dsa.string;

import java.util.Arrays;

public class ReverseWordsInAString {
    public static void main(String[] args) {
        String str = " hello world how are you ";
        reverseWords(str);
    }

    private static void reverseWords(String str) {
        System.out.println("input string is : "+str);
        String[] words = str.split(" ");
        System.out.println(Arrays.toString(words));
        StringBuilder sb = new StringBuilder();
        for(int i = words.length-1;i>=0;i--){
            if(words[i] != "") {
                sb.append(words[i].trim());
                if (i != 0) {
                    sb.append(" ");
                }
            }
        }
        System.out.println("output string is : "+sb.toString());
    }
}
