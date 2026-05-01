package com.java.poc.dsa.string.character_manipulation;

import java.util.HashMap;
import java.util.Map;

public class IsomorphicStrings {
    public static void main(String[] args) {
        String a = "egg";
        String b = "abb";
        System.out.println(checkIsomorphic(a,b));
    }

    public static boolean checkIsomorphic(String s, String t){
        Map<Character,Character> map1 = new HashMap<>();
        Map<Character,Character> map2 = new HashMap<>();

        if(s.length() != t.length()){
            return false;
        }

        for(int i=0;i<s.length();i++){
            char c1 = s.charAt(i);
            char c2 = t.charAt(i);

            if(map1.containsKey(c1)){
                if(map1.get(c1) != c2){
                    return false;
                }
            }else if(map2.containsKey(c2)){
                if(map2.get(c2) != c1){
                    return false;
                }
            }

            map1.put(c1,c2);
            map2.put(c2,c1);
        }

        return true;

    }
}
