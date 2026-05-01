package com.java.poc.design_collection;

import java.util.Comparator;

public class MyTreeMap<K, V> {

    private Node<K, V> root;
    private Comparator<? super K> comparator;

    // Constructor without comparator (natural order)
    public MyTreeMap() {
        this.comparator = null;
    }

    // Constructor with comparator
    public MyTreeMap(Comparator<? super K> comparator) {
        this.comparator = comparator;
    }

    // Node class representing each element in the tree
    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> left, right;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    // Helper method to compare keys
    private int compare(K k1, K k2) {
        if (comparator != null) {
            return comparator.compare(k1, k2);
        } else {
            Comparable<? super K> comp = (Comparable<? super K>) k1;
            return comp.compareTo(k2);
        }
    }

    // Put method to insert or update key-value pairs
    public void put(K key, V value) {
        root = put(root, key, value);
    }

    private Node<K, V> put(Node<K, V> node, K key, V value) {
        if (node == null) {
            return new Node<>(key, value);
        }

        int cmp = compare(key, node.key);
        if (cmp < 0) {
            node.left = put(node.left, key, value);
        } else if (cmp > 0) {
            node.right = put(node.right, key, value);
        } else {
            node.value = value; // Update value if the key is already present
        }
        return node;
    }

    // Get method to retrieve the value for a given key
    public V get(K key) {
        Node<K, V> node = get(root, key);
        return (node != null) ? node.value : null;
    }

    private Node<K, V> get(Node<K, V> node, K key) {
        if (node == null) {
            return null;
        }

        int cmp = compare(key, node.key);
        if (cmp < 0) {
            return get(node.left, key);
        } else if (cmp > 0) {
            return get(node.right, key);
        } else {
            return node;
        }
    }

    // Remove method to delete a key-value pair
    public void remove(K key) {
        root = remove(root, key);
    }

    private Node<K, V> remove(Node<K, V> node, K key) {
        if (node == null) {
            return null;
        }

        int cmp = compare(key, node.key);
        if (cmp < 0) {
            node.left = remove(node.left, key);
        } else if (cmp > 0) {
            node.right = remove(node.right, key);
        } else {
            // Node to be deleted found

            // Case 1: No children
            if (node.left == null && node.right == null) {
                return null;
            }

            // Case 2: One child
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }

            // Case 3: Two children - Find the smallest node in the right subtree
            Node<K, V> smallestNode = findMin(node.right);
            node.key = smallestNode.key;
            node.value = smallestNode.value;
            node.right = remove(node.right, smallestNode.key);
        }

        return node;
    }

    // Helper method to find the smallest node
    private Node<K, V> findMin(Node<K, V> node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // Helper method to perform an in-order traversal of the tree
    public void inOrderTraversal() {
        inOrderTraversal(root);
    }

    private void inOrderTraversal(Node<K, V> node) {
        if (node != null) {
            inOrderTraversal(node.left);
            System.out.println(node.key + " -> " + node.value);
            inOrderTraversal(node.right);
        }
    }

    public static void main(String[] args) {
        MyTreeMap<Integer, String> treeMap = new MyTreeMap<>();

        // Inserting elements
        treeMap.put(5, "Five");
        treeMap.put(3, "Three");
        treeMap.put(7, "Seven");
        treeMap.put(1, "One");
        treeMap.put(4, "Four");

        // Retrieving and displaying elements
        System.out.println("Value for key 3: " + treeMap.get(3));

        // In-order traversal (should be sorted by keys)
        System.out.println("In-order Traversal:");
        treeMap.inOrderTraversal();

        // Removing an element
        treeMap.remove(3);
        System.out.println("After removing key 3:");
        treeMap.inOrderTraversal();
    }
}
