package com.java.poc.curatedPracticeList.binary_tree_dfs;

/**
 * 1448. Count Good Nodes in Binary Tree
 * Medium
 * Topics
 * Companies
 * Hint
 * Given a binary tree root, a node X in the tree is named good if in the path from root to X there are no nodes with a value greater than X.
 *
 * Return the number of good nodes in the binary tree.
 *
 *
 *
 * Example 1:
 *
 *
 *
 * Input: root = [3,1,4,3,null,1,5]
 * Output: 4
 * Explanation: Nodes in blue are good.
 * Root Node (3) is always a good node.
 * Node 4 -> (3,4) is the maximum value in the path starting from the root.
 * Node 5 -> (3,4,5) is the maximum value in the path
 * Node 3 -> (3,1,3) is the maximum value in the path.
 * Example 2:
 *
 *
 *
 * Input: root = [3,3,null,4,2]
 * Output: 3
 * Explanation: Node 2 -> (3, 3, 2) is not good, because "3" is higher than it.
 * Example 3:
 *
 * Input: root = [1]
 * Output: 1
 * Explanation: Root is considered as good.
 *
 *
 * Constraints:
 *
 * The number of nodes in the binary tree is in the range [1, 10^5].
 * Each node's value is between [-10^4, 10^4].
 */
public class GoodNodeCounter {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    private int count = 0;

    public int goodNodes(TreeNode root) {
        if (root == null) return 0;
        dfs(root, root.val);
        return count;
    }

    private void dfs(TreeNode node, int maxSoFar) {
        if (node == null) return;

        if (node.val >= maxSoFar) count++;

        dfs(node.left, Math.max(maxSoFar, node.val));
        dfs(node.right, Math.max(maxSoFar, node.val));
    }

    private static TreeNode constructTree(Integer[] arr, int i) {
        if (i >= arr.length || arr[i] == null) return null;
        TreeNode root = new TreeNode(arr[i]);
        root.left = constructTree(arr, 2 * i + 1);
        root.right = constructTree(arr, 2 * i + 2);
        return root;
    }

    public static void main(String[] args) {
        GoodNodeCounter counter = new GoodNodeCounter();

        TreeNode root1 = constructTree(new Integer[]{3,1,4,3,null,1,5}, 0);
        assert counter.goodNodes(root1) == 4 : "Test case 1 failed";

        TreeNode root2 = constructTree(new Integer[]{3,3,null,4,2}, 0);
        assert counter.goodNodes(root2) == 3 : "Test case 2 failed";

        TreeNode root3 = constructTree(new Integer[]{1}, 0);
        assert counter.goodNodes(root3) == 1 : "Test case 3 failed";

        System.out.println("All test cases passed!");
    }
}
