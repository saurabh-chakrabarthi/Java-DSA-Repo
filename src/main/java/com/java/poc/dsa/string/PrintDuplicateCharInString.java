package com.java.poc.dsa.string;

import java.util.HashMap;
import java.util.Map;

public class PrintDuplicateCharInString {

    public static void main(String[] args) {

        String str = "aaabbc";
        char[] chars = str.toCharArray();

        Map<Character, Integer> map = new HashMap<>();

        for (char c : chars) {
            if (map.containsKey(c)) {
                int counter = map.get(c);
                map.put(c, ++counter);
            } else {
                map.put(c, 1);
            }
        }

        for (char ch : map.keySet()) {
            int count = map.get(ch);
            if (count > 1) {
                System.out.println("Duplicate Characters Found :: " + ch + ", Frequency of occurance : " + count);
            } else {
                System.out.println("Unique Characters Found  " + ch + ", Frequency of occurance : " + count);
            }
        }
    }
}
