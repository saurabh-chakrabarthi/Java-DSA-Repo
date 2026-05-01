package com.java.poc.design_collection;

import java.util.LinkedHashMap;
import java.util.Map;

public class MyLRUCacheUsingLinkedHashMap {

    int cap;
    LinkedHashMap<Integer,Integer> map = new LinkedHashMap<>();
    public MyLRUCacheUsingLinkedHashMap(int capacity) {
        cap=capacity;
    }

    public int get(int key) {
        if(map.containsKey(key)){
            int value = map.get(key);
            map.remove(key);
            map.put(key,value);
            return value;
        }
        return -1;
    }

    public void put(int key, int value) {
        if(map.containsKey(key)){
            map.remove(key);
        } else if(map.size() >= cap){
            Map.Entry<Integer,Integer> firstEntry = map.entrySet().iterator().next();
            map.remove(firstEntry.getKey());
        }
        map.put(key,value);
    }

    public static void main(String[] args) {
        MyLRUCacheUsingLinkedHashMap lruCache = new MyLRUCacheUsingLinkedHashMap(2); // Create an LRU Cache with a capacity of 2

        lruCache.put(1, 1); // Cache is {1=1}
        lruCache.put(2, 2); // Cache is {1=1, 2=2}
        System.out.println(lruCache.get(1)); // Return 1 and move key 1 to head, cache is {2=2, 1=1}
        lruCache.put(3, 3); // Evicts key 2, cache is {1=1, 3=3}
        System.out.println(lruCache.get(2)); // Return -1 (not found)
        lruCache.put(4, 4); // Evicts key 1, cache is {3=3, 4=4}
        System.out.println(lruCache.get(1)); // Return -1 (not found)
        System.out.println(lruCache.get(3)); // Return 3
        System.out.println(lruCache.get(4)); // Return 4
    }
}