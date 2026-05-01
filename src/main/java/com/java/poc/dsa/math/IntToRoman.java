package com.java.poc.dsa.math;

public class IntToRoman {

    private static String intToRoman(int num) {
        String M[] = {"", "M", "MM", "MMM"};
        String C[] = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String X[] = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String I[] = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        return M[num / 1000] + C[(num % 1000) / 100] + X[(num % 100) / 10] + I[num % 10];
    }

    public static void main(String[] args) {
        int input1 = 3;
        int input2 = 4;
        int input3 = 9;
        int input4 = 58;
        int input5 = 1994;
        System.out.println(input1 + " " + intToRoman(input1));
        System.out.println(input2 + " " + intToRoman(input2));
        System.out.println(input3 + " " + intToRoman(input3));
        System.out.println(input4 + " " + intToRoman(input4));
        System.out.println(input5 + " " + intToRoman(input5));

    }
}
