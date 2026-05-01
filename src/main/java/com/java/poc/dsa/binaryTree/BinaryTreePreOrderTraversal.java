package com.java.poc.dsa.binaryTree;

import java.util.ArrayList;
import java.util.List;

/**
 * 144. Binary Tree Preorder Traversal
 * Solved
 * Easy
 * Topics
 * Companies
 * Given the root of a binary tree, return the preorder traversal of its nodes' values.
 *
 *
 *
 * Example 1:
 *
 * Input: root = [1,null,2,3]
 *
 * Output: [1,2,3]
 *
 * Explanation:
 *
 *
 *
 * Example 2:
 *
 * Input: root = [1,2,3,4,5,null,8,null,null,6,7,9]
 *
 * Output: [1,2,4,5,6,7,3,8,9]
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
 *
 *
 * Follow up: Recursive solution is trivial, could you do it iteratively?
 */
public class BinaryTreePreOrderTraversal {

    public static void main(String[] args) {
        TreeNode treenode = new TreeNode(1);
        treenode.left = null;
        treenode.right = new TreeNode(2);
        treenode.right.left = new TreeNode(3);
        System.out.println(treenode);
        System.out.println(preOrderTraversal(treenode));

    }

    public static List<Integer> preOrderTraversal(TreeNode root) {
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
