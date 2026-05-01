package com.java.poc.curatedPracticeList.binary_tree_dfs;

/**
 * 104. Maximum Depth of Binary Tree
 * Solved
 * Easy
 * Topics
 * Companies
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
public class BinaryTreeDepthCalculator {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        return Math.max(left, right) + 1;
    }

    private static TreeNode constructTree(Integer[] arr, int i) {
        if (i >= arr.length || arr[i] == null) return null;
        TreeNode root = new TreeNode(arr[i]);
        root.left = constructTree(arr, 2 * i + 1);
        root.right = constructTree(arr, 2 * i + 2);
        return root;
    }

    public static void main(String[] args) {
        BinaryTreeDepthCalculator calculator = new BinaryTreeDepthCalculator();

        TreeNode root1 = constructTree(new Integer[]{3, 9, 20, null, null, 15, 7}, 0);
        assert calculator.maxDepth(root1) == 3 : "Test case 1 failed";

        TreeNode root2 = constructTree(new Integer[]{1, null, 2}, 0);
        assert calculator.maxDepth(root2) == 2 : "Test case 2 failed";

        System.out.println("All test cases passed!");
    }
}
