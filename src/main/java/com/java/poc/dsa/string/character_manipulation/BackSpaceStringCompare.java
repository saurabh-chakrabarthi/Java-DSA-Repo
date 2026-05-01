package com.java.poc.dsa.string.character_manipulation;

public class BackSpaceStringCompare {

    public static void main(String[] args) {
        System.out.println(backspaceCompare("abc##","hr#"));
    }

    public static boolean backspaceCompare(String s, String t) {
        return parse(s).equals(parse(t));
    }

    private static String parse(String str){
        StringBuilder sb = new StringBuilder();
        char[] chars = str.toCharArray();

        for(int i=0;i<str.length();i++){
            char ch = str.charAt(i);
            if(ch == '#'){
                if(sb.length() >= 1){
                    sb.deleteCharAt(sb.length()-1);
                }
            }
            else{
                sb.append(ch);
            }
        }

        return sb.toString();
    }
}
