package com.java.poc.dsa.binaryTree;

import java.util.ArrayList;
import java.util.List;

/**
 * 94. Binary Tree Inorder Traversal
 * Solved
 * Easy
 * Topics
 * Companies
 * Given the root of a binary tree, return the inorder traversal of its nodes' values.
 *
 *
 *
 * Example 1:
 *
 * Input: root = [1,null,2,3]
 *
 * Output: [1,3,2]
 *
 * Explanation:
 *
 *
 *
 * Example 2:
 *
 * Input: root = [1,2,3,4,5,null,8,null,null,6,7,9]
 *
 * Output: [4,2,6,5,7,1,3,9,8]
 *
 * Explanation:
 *
 *
 *
 * Example 3:
 *
 * Input: root = []
 *
 * Output: []
 *
 * Example 4:
 *
 * Input: root = [1]
 *
 * Output: [1]
 *
 *
 *
 * Constraints:
 *
 * The number of nodes in the tree is in the range [0, 100].
 * -100 <= Node.val <= 100
 */
public class BinaryTreeInOrderTraversal {

    public static void main(String[] args) {
        TreeNode treenode = new TreeNode(1);
        treenode.left = null;
        treenode.right = new TreeNode(2);
        treenode.right.left = new TreeNode(3);
        System.out.println(treenode);
        System.out.println(inorderTraversal(treenode));

    }

    public static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        dfs(result, root);
        return result;
    }

    public static void dfs(List<Integer> result, TreeNode node) {
        if(node != null){
            if(node.left != null) {
                dfs(result, node.left);
            }
            result.add(node.val);
            if(node.right != null) {
                dfs(result, node.right);
            }
        }
    }
}
