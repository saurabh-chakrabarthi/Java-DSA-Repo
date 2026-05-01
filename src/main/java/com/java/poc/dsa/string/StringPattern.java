package com.java.poc.dsa.string;

public class StringPattern {
    public static void main(String[] args) {

        convertToPattern("AAABBCCDEE");
    }

    private static String convertToPattern(String str){

        char[] chars = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        int counter = 1;
        for(int i = 0; i <chars.length-1; i++){
            if(chars[i] == chars[i+1]){
                counter++;

            }else{
                sb.append(chars[i]+String.valueOf(counter));
                counter = 1;
            }
        }
        System.out.println("Pattern is : "+sb.toString());
        return "";
    }
}
