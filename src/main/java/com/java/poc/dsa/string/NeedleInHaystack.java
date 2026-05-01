package com.java.poc.dsa.string;

public class NeedleInHaystack {

    public static void main(String[] args) {
        String haystack = "abc";
        String needle = "c";
        System.out.println(strStr(haystack,needle));
    }
    public static int strStr(String haystack, String needle) {

        if(needle.isEmpty() || haystack.equals(needle)){
            return 0;
        }

        if(haystack.contains(needle)){
            for(int i = 0; i<=haystack.length();i++){
                for(int j = i;j<=haystack.length();j++){
                    String subStr = haystack.substring(i,j);
                    if(subStr.equals(needle)){
                        return i;
                    }
                }
            }
        }else{
            return -1;
        }

        return 0;
    }
}
