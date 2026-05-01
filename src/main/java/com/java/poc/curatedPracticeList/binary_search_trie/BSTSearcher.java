package com.java.poc.curatedPracticeList.binary_search_trie;

/**
 * 700. Search in a Binary Search Tree
 * Solved
 * Easy
 * Topics
 * Companies
 * You are given the root of a binary search tree (BST) and an integer val.
 *
 * Find the node in the BST that the node's value equals val and return the subtree rooted with that node. If such a node does not exist, return null.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: root = [4,2,7,1,3], val = 2
 * Output: [2,1,3]
 * Example 2:
 *
 *
 * Input: root = [4,2,7,1,3], val = 5
 * Output: []
 *
 *
 * Constraints:
 *
 * The number of nodes in the tree is in the range [1, 5000].
 * 1 <= Node.val <= 107
 * root is a binary search tree.
 * 1 <= val <= 107
 */
public class BSTSearcher {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null || root.val == val) return root;
        return val < root.val ? searchBST(root.left, val) : searchBST(root.right, val);
    }

    private static TreeNode constructTree(Integer[] arr, int i) {
        if (i >= arr.length || arr[i] == null) return null;
        TreeNode root = new TreeNode(arr[i]);
        root.left = constructTree(arr, 2 * i + 1);
        root.right = constructTree(arr, 2 * i + 2);
        return root;
    }

    private static boolean isEqual(TreeNode a, TreeNode b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        return a.val == b.val && isEqual(a.left, b.left) && isEqual(a.right, b.right);
    }

    public static void main(String[] args) {
        BSTSearcher searcher = new BSTSearcher();

        TreeNode root1 = constructTree(new Integer[]{4,2,7,1,3}, 0);
        TreeNode expected1 = constructTree(new Integer[]{2,1,3}, 0);
        assert isEqual(searcher.searchBST(root1, 2), expected1) : "Test case 1 failed";

        TreeNode root2 = constructTree(new Integer[]{4,2,7,1,3}, 0);
        assert searcher.searchBST(root2, 5) == null : "Test case 2 failed";

        System.out.println("All test cases passed!");
    }
}
