package com.java.poc.dsa.string;

public class MultiplyStrings {
    private static int BASE = 10;
    public static void main(String[] args) {

        String a = "14";
        String b = "10";
        System.out.println("The multiplication of " + a + " and " + b + " is " + multiplyStrings(a, b));
    }

    public static String multiplyStrings(String num1, String num2) {
        StringBuilder sb = new StringBuilder();

        int i = num1.length() - 1;
        int j = num2.length() - 1;


        int carry = 0;
        while (i >= 0 || j >= 0) {
            int product = 1;
            if (i >= 0) product *= num1.charAt(i--) - '0';
            if (j >= 0) product *= num2.charAt(j--) - '0';

            carry = product / BASE;
            sb.append(product % BASE);
        }

        if (carry != 0) sb.append(carry);

        return sb.reverse().toString();
    }
}
