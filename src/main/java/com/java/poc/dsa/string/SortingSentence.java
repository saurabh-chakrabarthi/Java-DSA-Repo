package com.java.poc.dsa.string;

import java.util.HashMap;
import java.util.Map;

public class SortingSentence {

    public static void main(String[] args) {
        String s = "I4 Me1 and3 Myself2";
        System.out.println(sortSentence(s));
    }

    public static String sortSentence(String s) {
        StringBuilder sb = new StringBuilder();
        String[] strArray = s.split(" ");
        String[] strs = new String[strArray.length+1];
        Map<Integer,String> strMap = new HashMap<>();

        for(String str : strArray){
            String num = str.substring(str.length() - 1);
            String word = str.substring(0,str.length() - 1);
            strMap.put(Integer.valueOf(num),word);
        }

        for(int key : strMap.keySet()){
            strs[key] = strMap.get(key);
        }

        for(String st : strs){
            if(st != null){
                sb.append(st+ " ");
            }

        }

        return sb.toString().trim();
    }
}
