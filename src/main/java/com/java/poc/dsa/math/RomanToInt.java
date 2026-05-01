package com.java.poc.dsa.math;

import java.util.HashMap;
import java.util.Map;

public class RomanToInt {

    public static int romanToInt(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        Map<Character, Integer> map = new HashMap<>();
        map.put('M', 1000);
        map.put('D', 500);
        map.put('C', 100);
        map.put('L', 50);
        map.put('X', 10);
        map.put('V', 5);
        map.put('I', 1);

        int sum = 0;
        int prev = map.get(s.charAt(0));
        int next = 0;

        for (int i = 1; i < s.length(); i++) {
            next = map.get(s.charAt(i));

            if (prev < next) {
                sum -= prev;
            } else {
                sum += prev;
            }

            //update prev because it is like sliding window
            prev = next;
        }

        sum += prev;//corner case when only one digit, we need to let sum = prev, so we add prev, not next
        return sum;
    }

    public static void main(String[] args) {
        String input1 = "III";
        String input2 = "IV";
        String input3 = "IX";
        String input4 = "LVIII";
        String input5 = "MCMXCIV";
        System.out.println(input1 + " " + romanToInt(input1));
        System.out.println(input2 + " " + romanToInt(input2));
        System.out.println(input3 + " " + romanToInt(input3));
        System.out.println(input4 + " " + romanToInt(input4));
        System.out.println(input5 + " " + romanToInt(input5));

    }
}
