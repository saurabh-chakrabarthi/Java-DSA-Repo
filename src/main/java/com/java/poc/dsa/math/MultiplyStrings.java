package com.java.poc.dsa.math;

public class MultiplyStrings {
    public static void main(String[] args) {
        String n1 = "11";
        String n2 = "9";

        System.out.println("product is : "+multiplyStrs(n1,n2));
    }

    private static String multiplyStrs(String n1, String n2) {
        char[] num1 = n1.toCharArray();
        char[] num2 = n2.toCharArray();
        int[] intArr = new int[n1.length() + n2.length()];
        StringBuilder sb = new StringBuilder();

        int sum = 0;
        for(int i = num1.length - 1;i >= 0; i--){
            int carry = 0;
            for(int j =  num1.length - 1;j>=0;j--){
                int tmp = num1[i] * num2[j];
                if(tmp >= 10){
                   carry = carry + tmp/10;
                    sb.append((tmp+carry)%10);
                }
            }
        }
        return sb.reverse().toString();
    }
}
