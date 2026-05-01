package com.java.poc.curatedPracticeList.binary_tree_bfs;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.IntStream;

/**
 * 1161. Maximum Level Sum of a Binary Tree
 * Medium
 * Topics
 * Companies
 * Hint
 * Given the root of a binary tree, the level of its root is 1, the level of its children is 2, and so on.
 *
 * Return the smallest level x such that the sum of all the values of nodes at level x is maximal.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: root = [1,7,0,7,-8,null,null]
 * Output: 2
 * Explanation:
 * Level 1 sum = 1.
 * Level 2 sum = 7 + 0 = 7.
 * Level 3 sum = 7 + -8 = -1.
 * So we return the level with the maximum sum which is level 2.
 * Example 2:
 *
 * Input: root = [989,null,10250,98693,-89388,null,null,null,-32127]
 * Output: 2
 *
 *
 * Constraints:
 *
 * The number of nodes in the tree is in the range [1, 104].
 * -105 <= Node.val <= 105
 */
public class MaxLevelSumCalculator {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public int maxLevelSum(TreeNode root) {
        int max = Integer.MIN_VALUE, maxLevel = 1;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        for (int level = 1; !q.isEmpty(); ++level) {
            int sum = 0;
            for (int sz = q.size(); sz > 0; --sz) {
                TreeNode n = q.poll();
                sum += n.val;
                if (n.left != null) {
                    q.offer(n.left);
                }
                if (n.right != null) {
                    q.offer(n.right);
                }
            }
            if (max < sum) {
                max = sum;
                maxLevel = level;
            }
        }
        return maxLevel;
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
        MaxLevelSumCalculator calculator = new MaxLevelSumCalculator();

        TreeNode root1 = createTreeFromArray(new Integer[]{1, 7, 0, null, 5, null, 4}, 0);
        assert calculator.maxLevelSum(root1) == 2 : "Test case 1 failed";

        TreeNode root2 = createTreeFromArray(new Integer[]{989, null, 10250, 98693, -89388, null, null, null, -32127}, 0);
        assert calculator.maxLevelSum(root2) == 2 : "Test case 2 failed";

        System.out.println("All test cases passed!");
    }
}
