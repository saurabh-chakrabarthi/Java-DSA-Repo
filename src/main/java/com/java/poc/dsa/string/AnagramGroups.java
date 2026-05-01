package com.java.poc.dsa.string;

import java.util.*;

public class AnagramGroups {
    public static void main(String[] args) {
        String[] strArray = new String[]{"art","cheese","cat","rat","tar","act","tca"};

        groupAnagrams(strArray);
    }

    private static void groupAnagrams(String[] strArray) {
        Map<String, List<String>> strMap = new HashMap<>();
        StringBuffer sb;

        for(String str : strArray){
            sb = new StringBuffer();
            char[] chars = str.toCharArray();
            Arrays.sort(chars);

            for(char c : chars){
                sb.append(c);
            }

            if(strMap.containsKey(sb.toString())){
                strMap.get(sb.toString()).add(str);
            }else{
                List<String> strList = new ArrayList<String>();
                strList.add(str);
                strMap.put(sb.toString(),strList);
            }
        }

        System.out.println(strMap.toString());
    }
}
