package com.java.poc.dsa.linkedlist;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Leetcode 146. LRU Cache
 * Design a data structure that follows the constraints of a Least Recently Used (LRU) cache.
 *
 * Implement the LRUCacheUsingLinkedHashMap class:
 *
 * LRUCacheUsingLinkedHashMap(int capacity) Initialize the LRU cache with positive size capacity.
 * int get(int key) Return the value of the key if the key exists, otherwise return -1.
 * void put(int key, int value) Update the value of the key if the key exists. Otherwise, add the key-value pair to the cache. If the number of keys exceeds the capacity from this operation, evict the least recently used key.
 * The functions get and put must each run in O(1) average time complexity.
 *
 *
 *
 * Example 1:
 *
 * Input
 * ["LRUCacheUsingLinkedHashMap", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
 * [[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
 * Output
 * [null, null, null, 1, null, -1, null, -1, 3, 4]
 *
 * Explanation
 * LRUCacheUsingLinkedHashMap lRUCache = new LRUCacheUsingLinkedHashMap(2);
 * lRUCache.put(1, 1); // cache is {1=1}
 * lRUCache.put(2, 2); // cache is {1=1, 2=2}
 * lRUCache.get(1);    // return 1
 * lRUCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
 * lRUCache.get(2);    // returns -1 (not found)
 * lRUCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
 * lRUCache.get(1);    // return -1 (not found)
 * lRUCache.get(3);    // return 3
 * lRUCache.get(4);    // return 4
 *
 *
 * Constraints:
 *
 * 1 <= capacity <= 3000
 * 0 <= key <= 104
 * 0 <= value <= 105
 * At most 2 * 105 calls will be made to get and put.
 */
class LRUCacheUsingLinkedHashMap {
    Map<Integer,Integer> map = new LinkedHashMap<>(16, 0.75f, true);
    int cap;
    public LRUCacheUsingLinkedHashMap(int capacity) {
        this.cap = capacity;
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
        }else if(map.size() >= cap){
            map.remove(map.entrySet().iterator().next().getKey());
        }

        map.put(key,value);
    }

    public static void main(String[] args) {
        LRUCacheUsingLinkedHashMap lruCache = new LRUCacheUsingLinkedHashMap(2);
        lruCache.put(1, 1); // cache is {1=1}
        lruCache.put(2, 2); // cache is {1=1, 2=2}
        System.out.println(lruCache.get(1));    // return 1
        lruCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
        System.out.println(lruCache.get(2));    // returns -1 (not found)
        lruCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
        System.out.println(lruCache.get(1));    // return -1 (not found)
        System.out.println(lruCache.get(3));    // return 3
        System.out.println(lruCache.get(4));    // return 4
    }
}
