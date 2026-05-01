package com.java.poc.dsa.greedy;

import java.util.Arrays;

public class AssignCookies {
    public static void main(String[] args) {
        int[] g = new int[]{1,2,3};
        int[] s = new int[]{1,1};
        System.out.println("number of content children : "+findContentChildren(g,s));
    }
    public static int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int i = 0;
        for(int j=0;i<g.length && j<s.length;j++) {
            if(g[i]<=s[j]) i++;
        }
        return i;
    }
}
