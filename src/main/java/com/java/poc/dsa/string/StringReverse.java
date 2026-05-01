package com.java.poc.dsa.string;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class StringReverse {
    public static void main(String[] args) {

        String str = "abcde";
        StringBuilder stringBuilder = new StringBuilder(str);
        System.out.println(stringBuilder.reverse());

        String input = "GeeksforGeeks";
        byte[] strAsByteArray = input.getBytes();
        byte[] result = new byte[strAsByteArray.length];

        for (int i = 0; i < strAsByteArray.length; i++)
            result[i] = strAsByteArray[strAsByteArray.length - i - 1];
        System.out.println(new String(result));

        char[] try1 = input.toCharArray();
        for (int i = try1.length - 1; i >= 0; i--)
            System.out.print(try1[i]);

        char[] temparray = input.toCharArray();
        int left, right = 0;
        right = temparray.length - 1;

        for (left = 0; left < right; left++, right--) {
            char temp = temparray[left];
            temparray[left] = temparray[right];
            temparray[right] = temp;
        }
        for (char c : temparray)
            System.out.print(c);
        System.out.println();


        char[] hello = input.toCharArray();
        List<Character> trial1 = new ArrayList<>();

        for (char c : hello)
            trial1.add(c);

        Collections.reverse(trial1);
        ListIterator li = trial1.listIterator();
        while (li.hasNext())
            System.out.print(li.next());
    }
}
