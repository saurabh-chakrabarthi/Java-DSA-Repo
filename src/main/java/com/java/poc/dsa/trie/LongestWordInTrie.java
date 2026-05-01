package com.java.poc.dsa.trie;

public class LongestWordInTrie {
    private static TrieNode root = new TrieNode();
    private static String longestWord = "";

    public static void main(String[] args) {
        String[] words = {"w","wo","wor","worl","world"};
        String result = longestWord(words);
        System.out.println("Longest Word: " + result); // Output: "world"
    }

    private static String longestWord(String[] words) {
        // Build the Trie
        for(String word : words) {
            insert(word);
        }

        printTrie(root,"");

        // DFS to find the longest word
        dfs(root);

        return longestWord;
    }

    private static void insert(String word) {
        TrieNode node = root;
        for(char ch : word.toCharArray()) {
            int index = ch - 'a';
            if(node.children[index] == null) {
                node.children[index] = new TrieNode();
            }
            node = node.children[index];
        }
        node.isEndOfWord = true;
        node.word = word;
    }

    private static void dfs(TrieNode node) {
        if(node == null) return;

        if(node.word != null) {
            if(node.word.length() > longestWord.length() ||
               (node.word.length() == longestWord.length() && node.word.compareTo(longestWord) < 0)) {
                longestWord = node.word;
            }
        }

        for(TrieNode child : node.children) {
            if(child != null && child.isEndOfWord) {
                dfs(child);
            }
        }
    }

    private static void printTrie(TrieNode node, String prefix) {
        if (node == null) return;

        if (node.word != null) {
            System.out.println(prefix + "-->" + node.word);
        }

        for (int i = 0; i < 26; i++) {
            if (node.children[i] != null) {
                printTrie(node.children[i], prefix + (char) (i + 'a'));
            }
        }
    }
}
