package com.java.poc.dsa.math;

public class NumberCompliment {
    public static void main(String[] args) {
        int num = 5;
        System.out.println("Input : "+num);
        //findComplement(num);
        System.out.println("Output : "+findComplementV2(num));
    }

    public static int findComplement(int num) {
        String binary = Integer.toBinaryString(num);
        System.out.println("Binary of input : "+binary);

        char[] chars = binary.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(char c : chars){
            if(c =='0'){
                sb.append('1');
            }else{
                sb.append('0');
            }
        }
        System.out.println("Compliment of input : "+sb);
        return toDecimal(sb.toString());
    }

    public static int toDecimal(String str){
        int num = 0;
        char[] chars = str.toCharArray();
        int len = chars.length;
        for(int i=0;i<chars.length;i++){
            num = num + (chars[len-i-1] - '0') * (int) Math.pow(2, i);
        }
        System.out.println("Decimal of compliment : "+num);

        return num;
    }

    public static int findComplementV2(int num) {
        String s1 = Integer.toBinaryString(num);
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<s1.length();i++){
            if(s1.charAt(i)=='0'){
                sb.append('1');
            } else {
                sb.append('0');
            }
        }
        return Integer.parseInt(sb.toString(),2);
    }
}
