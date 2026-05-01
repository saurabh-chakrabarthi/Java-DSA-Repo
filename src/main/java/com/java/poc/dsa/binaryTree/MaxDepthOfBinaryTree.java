package com.java.poc.dsa.binaryTree;

/**
 * Leetcode 104. Maximum Depth of Binary Tree
 *
 *  This is a DFS approach
 *
 * Given the root of a binary tree, return its maximum depth.
 *
 * A binary tree's maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: root = [3,9,20,null,null,15,7]
 * Output: 3
 * Example 2:
 *
 * Input: root = [1,null,2]
 * Output: 2
 *
 *
 * Constraints:
 *
 * The number of nodes in the tree is in the range [0, 104].
 * -100 <= Node.val <= 100
 */
public class MaxDepthOfBinaryTree {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        System.out.println("Max depth of binary tree: " + maxDepthUsingDFS(root));
    }

    private static int maxDepthUsingDFS(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftDepth = maxDepthUsingDFS(root.left);
        int rightDepth = maxDepthUsingDFS(root.right);
        return Math.max(leftDepth, rightDepth) + 1;
    }
}
