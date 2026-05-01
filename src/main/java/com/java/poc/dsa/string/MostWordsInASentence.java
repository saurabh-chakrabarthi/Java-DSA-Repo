package com.java.poc.dsa.string;

public class MostWordsInASentence {

    public static void main(String[] args) {
        String[] sentences = new String[]{"alice and bob love leetcode","i think so too","this is great thanks very much"};
        System.out.println(mostWordsFound(sentences));

    }

    public static int mostWordsFound(String[] sentences) {
        int max = 0;
        for(String str : sentences){
            String[] words = str.split(" ");
            if(max < words.length){
                max = words.length;
            }
        }

        return max;
    }
}
