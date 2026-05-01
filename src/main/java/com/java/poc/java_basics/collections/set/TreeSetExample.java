package com.java.poc.java_basics.collections.set;

import java.util.Iterator;
import java.util.TreeSet;

public class TreeSetExample {
    public static void main(String args[]){
//Creating and adding elements
        TreeSet<String> set=new TreeSet<String>();
        set.add("Ravi");
        set.add("Vijay");
        set.add("Ravi");
        set.add("Ajay");
//traversing elements
        Iterator<String> itr=set.iterator();
        while(itr.hasNext()){
            System.out.println(itr.next());
        }
    }
}
