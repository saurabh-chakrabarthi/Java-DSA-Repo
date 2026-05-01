package com.java.poc.curatedPracticeList.binary_tree_bfs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 199. Binary Tree Right Side View
 * Medium
 * Topics
 * Companies
 * Given the root of a binary tree, imagine yourself standing on the right side of it, return the values of the nodes you can see ordered from top to bottom.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: root = [1,2,3,null,5,null,4]
 * Output: [1,3,4]
 * Example 2:
 *
 * Input: root = [1,null,3]
 * Output: [1,3]
 * Example 3:
 *
 * Input: root = []
 * Output: []
 *
 *
 * Constraints:
 *
 * The number of nodes in the tree is in the range [0, 100].
 * -100 <= Node.val <= 100
 */
public class RightViewTree {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public List<Integer> rightSideView(TreeNode root) {
        if (root == null)
            return new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        List<Integer> res = new ArrayList<>();

        while (!queue.isEmpty()) {
            int size = queue.size();

            while (size-- > 0) {
                TreeNode cur = queue.poll();
                if (size == 0)
                    res.add(cur.val);

                if (cur.left != null)
                    queue.offer(cur.left);
                if (cur.right != null)
                    queue.offer(cur.right);
            }
        }

        return res;
    }

    private static TreeNode createTreeFromArray(Integer[] arr, int index) {
        if (index < arr.length) {
            Integer value = arr[index];
            if (value == null) {
                return null;
            }
            TreeNode t = new TreeNode(value);
            t.left = createTreeFromArray(arr, 2 * index + 1);
            t.right = createTreeFromArray(arr, 2 * index + 2);
            return t;
        }
        return null;
    }

    public static void main(String[] args) {
        RightViewTree rightViewTree = new RightViewTree();

        TreeNode root1 = createTreeFromArray(new Integer[]{1, 2, 3, null, 5, null, 4}, 0);
        assert rightViewTree.rightSideView(root1).equals(List.of(1, 3, 4)) : "Test case 1 failed";

        TreeNode root2 = createTreeFromArray(new Integer[]{1, null, 3}, 0);
        assert rightViewTree.rightSideView(root2).equals(List.of(1, 3)) : "Test case 2 failed";

        TreeNode root3 = createTreeFromArray(new Integer[]{}, 0);
        assert rightViewTree.rightSideView(root3).equals(List.of()) : "Test case 3 failed";

        System.out.println("All test cases passed!");
    }
}
