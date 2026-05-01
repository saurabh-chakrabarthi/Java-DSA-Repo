package com.java.poc.dsa.binaryTree;

import java.util.ArrayList;
import java.util.List;

/**
 * 145. Binary Tree Postorder Traversal
 * Solved
 * Easy
 * Topics
 * Companies
 * Given the root of a binary tree, return the postorder traversal of its nodes' values.
 *
 *
 *
 * Example 1:
 *
 * Input: root = [1,null,2,3]
 *
 * Output: [3,2,1]
 *
 * Explanation:
 *
 *
 *
 * Example 2:
 *
 * Input: root = [1,2,3,4,5,null,8,null,null,6,7,9]
 *
 * Output: [4,6,7,5,2,9,8,3,1]
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
 * The number of the nodes in the tree is in the range [0, 100].
 * -100 <= Node.val <= 100
 */
public class BinaryTreePostOrderTraversal {

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
