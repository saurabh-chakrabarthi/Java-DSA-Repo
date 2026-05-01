package com.java.poc.dsa.string.character_manipulation;

public class AddStrings {
    private static int BASE = 10;

    public static void main(String[] args) {

        String a = "14";
        String b = "17";
        System.out.println("The addition of " + a + " and " + b + " is " + addStrings(a, b));
    }

    public static String addStrings(String num1, String num2) {
        StringBuilder sb = new StringBuilder();

        int i = num1.length() - 1;
        int j = num2.length() - 1;

        int sum = 0;
        int carry = 0;
        while (i >= 0 || j >= 0) {
            sum = carry;

            if (i >= 0) sum += num1.charAt(i--) - '0';
            if (j >= 0) sum += num2.charAt(j--) - '0';

            carry = sum / BASE;
            sb.append(sum % BASE);
        }

        if (carry != 0) sb.append(carry);

        return sb.reverse().toString();
    }
}
