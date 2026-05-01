package com.java.poc.dsa.string.character_manipulation;

import java.util.ArrayList;
import java.util.List;

public class EncodeDecodeListOfStrings {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("World");
        list.add("Java");
        System.out.println(encode(list));
        System.out.println(decode(encode(list)));
    }

    public static String encode(List<String> strs) {
        StringBuilder sb = new StringBuilder();
        for (String s : strs) {
            sb.append(s.length()).append('#').append(s);
        }
        return sb.toString();
    }

    // Decode using length parsing
    public static List<String> decode(String str) {
        List<String> result = new ArrayList<>();
        int i = 0;
        while (i < str.length()) {
            int j = i;
            // Find the '#' separator
            while (str.charAt(j) != '#') {
                j++;
            }
            int len = Integer.parseInt(str.substring(i, j));
            String word = str.substring(j + 1, j + 1 + len);
            result.add(word);
            i = j + 1 + len;
        }
        return result;
    }
}
