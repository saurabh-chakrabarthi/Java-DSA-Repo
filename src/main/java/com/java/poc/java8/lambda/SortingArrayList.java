package com.java.poc.java8.lambda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortingArrayList {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(7);
        list.add(3);
        list.add(2);
        list.add(6);
        list.add(5);

        System.out.println("Before Sorting : "+list.toString());

        // Default Natural Sorting
        //Collections.sort(list);

        // Custom Natural Sorting
        Collections.sort(list,new MyComparator());
        //System.out.println("After Sorting : "+list.toString());

        // Custom Natural Sorting using Lambda Expression
        Collections.sort(list,(x,y)->x.compareTo(y));
        System.out.println("After Sorting using Lambda Expression: "+list.toString());
    }
}
