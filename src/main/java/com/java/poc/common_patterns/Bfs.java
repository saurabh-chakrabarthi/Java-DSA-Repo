package com.java.poc.common_patterns;

import com.java.poc.dsa.binaryTree.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Bfs {

    public static void main(String[] args) {

        TreeNode treenode = new TreeNode(24);
        treenode.left = new TreeNode(16);
        treenode.right = new TreeNode(30);
        treenode.left.left = new TreeNode(13);
        treenode.left.right = new TreeNode(20);
        treenode.right.left = new TreeNode(25);
        System.out.println(treenode);
        System.out.println("My Tree : "+bfsTraversal(treenode));
    }

    public static List<List<Integer>> bfsTraversal(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> currentLevel = new ArrayList<>();

            for (int i = 0; i < levelSize; i++) {
                TreeNode currentNode = queue.poll();
                currentLevel.add(currentNode.val);

                // Add left and right children to the queue
                if (currentNode.left != null) queue.offer(currentNode.left);
                if (currentNode.right != null) queue.offer(currentNode.right);
            }

            result.add(currentLevel); // Add the current level to the result
        }

        return result;
    }

}
