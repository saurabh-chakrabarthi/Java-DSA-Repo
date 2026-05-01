package com.java.poc.curatedPracticeList.binary_tree_dfs;

/**
 * 236. Lowest Common Ancestor of a Binary Tree
 * Medium
 * Topics
 * Companies
 * Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.
 *
 * According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two nodes p and q as the lowest node in T that has both p and q as descendants (where we allow a node to be a descendant of itself).”
 *
 *
 *
 * Example 1:
 *
 *
 * Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
 * Output: 3
 * Explanation: The LCA of nodes 5 and 1 is 3.
 * Example 2:
 *
 *
 * Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
 * Output: 5
 * Explanation: The LCA of nodes 5 and 4 is 5, since a node can be a descendant of itself according to the LCA definition.
 * Example 3:
 *
 * Input: root = [1,2], p = 1, q = 2
 * Output: 1
 *
 *
 * Constraints:
 *
 * The number of nodes in the tree is in the range [2, 105].
 * -109 <= Node.val <= 109
 * All Node.val are unique.
 * p != q
 * p and q will exist in the tree.
 */
public class LowestCommonAncestorFinder {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }


    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) return root;
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        return left == null ? right : right == null ? left : root;
    }

    private static TreeNode constructTree(Integer[] arr, int i) {
        if (i >= arr.length || arr[i] == null) return null;
        TreeNode root = new TreeNode(arr[i]);
        root.left = constructTree(arr, 2 * i + 1);
        root.right = constructTree(arr, 2 * i + 2);
        return root;
    }

    private static TreeNode findNode(TreeNode root, int val) {
        if (root == null || root.val == val) return root;
        TreeNode left = findNode(root.left, val);
        TreeNode right = findNode(root.right, val);
        return left != null ? left : right;
    }

    public static void main(String[] args) {
        LowestCommonAncestorFinder finder = new LowestCommonAncestorFinder();

        TreeNode root1 = constructTree(new Integer[]{3,5,1,6,2,0,8,null,null,7,4}, 0);
        assert finder.lowestCommonAncestor(root1, findNode(root1, 5), findNode(root1, 1)).val == 3 : "Test case 1 failed";

        TreeNode root2 = constructTree(new Integer[]{3,5,1,6,2,0,8,null,null,7,4}, 0);
        assert finder.lowestCommonAncestor(root2, findNode(root2, 5), findNode(root2, 4)).val == 5 : "Test case 2 failed";

        TreeNode root3 = constructTree(new Integer[]{1,2}, 0);
        assert finder.lowestCommonAncestor(root3, findNode(root3, 1), findNode(root3, 2)).val == 1 : "Test case 3 failed";

        System.out.println("All test cases passed!");
    }
}
