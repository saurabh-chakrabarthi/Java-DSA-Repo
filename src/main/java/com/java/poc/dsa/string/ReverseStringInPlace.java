package com.java.poc.dsa.string;

public class ReverseStringInPlace {
    public static void main(String[] args) {
        String str = "hello";
        reverseStr(str);
    }

    private static void reverseStr(String str) {
        StringBuilder sb = new StringBuilder();
        char[] tmp = new char[str.length()];
        char[] chars = str.toCharArray();
        for(int i=0;i<str.length()/2;i++){
            tmp[i] = chars[i];
            chars[i] = chars[str.length() - 1 - i];
            chars[str.length() - 1 - i] = tmp[i];
        }

        for(char c : chars){
            sb.append(c);
        }

        System.out.println("original string is "+str);
        System.out.println("reversed string is "+sb.toString());

    }
}
