package com.java.poc.dsa.string;

import java.util.LinkedHashMap;
import java.util.Map;

public class IntegerToRoman {

    public static void main(String[] args) {
        int num = 3749;
        System.out.println("Integer: " + num);
        System.out.println("Roman: " + intToRoman(num));
        System.out.println("Roman2: " + intToRoman2(num));
    }

    public static String intToRoman(int num) {
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanLiterals = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < values.length; i++) {
            while (num >= values[i]) {
                num -= values[i];
                sb.append(romanLiterals[i]);
            }
        }

        return sb.toString();
    }

    public static String intToRoman2(int num) {
        // Use LinkedHashMap to maintain order
        Map<Integer, String> map = new LinkedHashMap<>();
        map.put(1000, "M");
        map.put(900, "CM");
        map.put(500, "D");
        map.put(400, "CD");
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");

        StringBuilder sb = new StringBuilder();

        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            int value = entry.getKey();
            String symbol = entry.getValue();

            while (num >= value) {
                num -= value;
                sb.append(symbol);
            }
        }

        return sb.toString();
    }
}
