package com.java.poc.curatedPracticeList.binary_tree_dfs;

/**
 * 1372. Longest ZigZag Path in a Binary Tree
 * Medium
 * Topics
 * Companies
 * Hint
 * You are given the root of a binary tree.
 *
 * A ZigZag path for a binary tree is defined as follow:
 *
 * Choose any node in the binary tree and a direction (right or left).
 * If the current direction is right, move to the right child of the current node; otherwise, move to the left child.
 * Change the direction from right to left or from left to right.
 * Repeat the second and third steps until you can't move in the tree.
 * Zigzag length is defined as the number of nodes visited - 1. (A single node has a length of 0).
 *
 * Return the longest ZigZag path contained in that tree.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: root = [1,null,1,1,1,null,null,1,1,null,1,null,null,null,1]
 * Output: 3
 * Explanation: Longest ZigZag path in blue nodes (right -> left -> right).
 * Example 2:
 *
 *
 * Input: root = [1,1,1,null,1,null,null,1,1,null,1]
 * Output: 4
 * Explanation: Longest ZigZag path in blue nodes (left -> right -> left -> right).
 * Example 3:
 *
 * Input: root = [1]
 * Output: 0
 *
 *
 * Constraints:
 *
 * The number of nodes in the tree is in the range [1, 5 * 104].
 * 1 <= Node.val <= 100
 */
public class ZigZagPathCalculator {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    private int maxStep = 0;

    public int longestZigZag(TreeNode root) {
        dfs(root, true, 0);
        dfs(root, false, 0);
        return maxStep;
    }

    private void dfs(TreeNode node, boolean isLeft, int step) {
        if (node == null) return;
        maxStep = Math.max(maxStep, step);
        if (isLeft) {
            dfs(node.left, false, step + 1);
            dfs(node.right, true, 1);
        } else {
            dfs(node.right, true, step + 1);
            dfs(node.left, false, 1);
        }
    }

    private static TreeNode constructTree(Integer[] arr, int i) {
        if (i >= arr.length || arr[i] == null) return null;
        TreeNode root = new TreeNode(arr[i]);
        root.left = constructTree(arr, 2 * i + 1);
        root.right = constructTree(arr, 2 * i + 2);
        return root;
    }

    public static void main(String[] args) {
        ZigZagPathCalculator calculator = new ZigZagPathCalculator();

        TreeNode root1 = constructTree(new Integer[]{1,null,1,1,1,null,null,1,1,null,1,null,null,null,1}, 0);
        assert calculator.longestZigZag(root1) == 3 : "Test case 1 failed";

        TreeNode root2 = constructTree(new Integer[]{1,1,1,null,1,null,null,1,1,null,1}, 0);
        assert calculator.longestZigZag(root2) == 4 : "Test case 2 failed";

        TreeNode root3 = constructTree(new Integer[]{1}, 0);
        assert calculator.longestZigZag(root3) == 0 : "Test case 3 failed";

        System.out.println("All test cases passed!");
    }
}
