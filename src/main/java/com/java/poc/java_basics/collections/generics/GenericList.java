package com.java.poc.java_basics.collections.generics;

import java.util.ArrayList;
import java.util.List;

public class GenericList {
    public static void main(String[] args) {
        List myList = new ArrayList();
        myList.add("hello");
        myList.add("world");
        myList.add(2024);

        uppercase(myList);
    }

    public static void uppercase(List myList){
        for(Object i : myList){
            System.out.println(i);
        }
    }
}
