package com.java.poc.java_basics.collections.hashtable;

import java.util.Hashtable;
import java.util.Map;

public class HashTableExample {
    public static void main(String args[]){
        Hashtable<Integer,String> hashtable=new Hashtable<Integer,String>();

        hashtable.put(100,"Amit");
        hashtable.put(102,"Ravi");
        hashtable.put(101,"Vijay");
        hashtable.put(103,"Rahul");

        for(Map.Entry m:hashtable.entrySet()){
            System.out.println(m.getKey()+" "+m.getValue());
        }

        System.out.println("Before remove: "+ hashtable);
        // Remove value for key 102
        hashtable.remove(102);
        System.out.println("After remove: "+ hashtable);

        System.out.println(hashtable.getOrDefault(101, "Not Found"));
        System.out.println(hashtable.getOrDefault(105, "Not Found"));

        //Inserts, as the specified pair is unique
        hashtable.putIfAbsent(104,"Gaurav");
        System.out.println("Updated Map: "+hashtable);
        //Returns the current value, as the specified pair already exist
        hashtable.putIfAbsent(101,"Vijay");
        System.out.println("Updated Map: "+hashtable);
    }
}
