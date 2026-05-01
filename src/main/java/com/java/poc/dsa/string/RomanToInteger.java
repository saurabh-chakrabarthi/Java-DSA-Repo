package com.java.poc.dsa.string;

import java.util.LinkedHashMap;
import java.util.Map;

public class RomanToInteger {

    public static void main(String[] args) {
        String roman = "MMMDCCXLIX";
        System.out.println("Roman: " + roman);
        System.out.println("Integer: " + RomanToInt(roman));
    }


    public static int RomanToInt(String roman) {
        // Use LinkedHashMap to maintain order
        Map<String,Integer> map = new LinkedHashMap<>();
        map.put("M", 1000);
        map.put("CM", 900);
        map.put("D", 500);
        map.put("CD", 400);
        map.put("C", 100);
        map.put("XC", 90);
        map.put("L", 50);
        map.put("XL", 40);
        map.put("X", 10);
        map.put("V", 5);
        map.put("IV", 4);
        map.put("I", 1);

        int result = 0;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String symbol =  entry.getKey();
            int value = entry.getValue();
            while (roman.startsWith(symbol)) {
                result += value;
                roman = roman.substring(symbol.length());
            }
        }
        return result;
    }
}
