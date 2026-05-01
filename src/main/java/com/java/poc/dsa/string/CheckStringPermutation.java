package com.java.poc.dsa.string;

import java.util.Arrays;

public class CheckStringPermutation {

    public static void main(String[] args) {
        String a = "rat";
        String b = "art";

        if(checkPermutation(a,b)){
            System.out.println("The two Strings are permutations");
        }else{
            System.out.println("The two Strings are NOT permutations");
        }

    }

    private static boolean checkPermutation(String a, String b) {
        char[] char1 = a.toCharArray();
        char[] char2 = b.toCharArray();
        Arrays.sort(char1);
        Arrays.sort(char2);

        for(int i=0;i<a.length()-1;i++){
            if(char1[i] != char2[i]){
                return false;
            }
        }
        return true;
    }

}
