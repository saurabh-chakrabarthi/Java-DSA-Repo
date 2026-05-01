package com.java.poc.dsa.trie;

public class TrieNode {
    TrieNode[] children;
    boolean isEndOfWord;
    String word = null;

    public TrieNode() {
        children = new TrieNode[26]; // Assuming only lowercase English letters
        isEndOfWord = false;
    }
}
