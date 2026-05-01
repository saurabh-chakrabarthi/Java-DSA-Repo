package com.java.poc.curatedPracticeList.binary_search_trie;

/**
 * 450. Delete Node in a BST
 * Medium
 * Topics
 * Companies
 * Given a root node reference of a BST and a key, delete the node with the given key in the BST. Return the root node reference (possibly updated) of the BST.
 *
 * Basically, the deletion can be divided into two stages:
 *
 * Search for a node to remove.
 * If the node is found, delete the node.
 *
 *
 * Example 1:
 *
 *
 * Input: root = [5,3,6,2,4,null,7], key = 3
 * Output: [5,4,6,2,null,null,7]
 * Explanation: Given key to delete is 3. So we find the node with value 3 and delete it.
 * One valid answer is [5,4,6,2,null,null,7], shown in the above BST.
 * Please notice that another valid answer is [5,2,6,null,4,null,7] and it's also accepted.
 *
 * Example 2:
 *
 * Input: root = [5,3,6,2,4,null,7], key = 0
 * Output: [5,3,6,2,4,null,7]
 * Explanation: The tree does not contain a node with value = 0.
 * Example 3:
 *
 * Input: root = [], key = 0
 * Output: []
 *
 *
 * Constraints:
 *
 * The number of nodes in the tree is in the range [0, 104].
 * -105 <= Node.val <= 105
 * Each node has a unique value.
 * root is a valid binary search tree.
 * -105 <= key <= 105
 *
 *
 * Follow up: Could you solve it with time complexity O(height of tree)?
 */
public class BSTNodeDeleter {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) return null;

        if (root.val > key) {
            root.left = deleteNode(root.left, key);
        } else if (root.val < key) {
            root.right = deleteNode(root.right, key);
        } else {
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;

            TreeNode rightSmallest = root.right;
            while (rightSmallest.left != null) rightSmallest = rightSmallest.left;
            rightSmallest.left = root.left;
            return root.right;
        }
        return root;
    }

    private static TreeNode constructTree(Integer[] arr, int i) {
        if (i >= arr.length || arr[i] == null) return null;
        TreeNode root = new TreeNode(arr[i]);
        root.left = constructTree(arr, 2 * i + 1);
        root.right = constructTree(arr, 2 * i + 2);
        return root;
    }

    public static void main(String[] args) {
        BSTNodeDeleter deleter = new BSTNodeDeleter();

        TreeNode root1 = constructTree(new Integer[]{5,3,6,2,4,null,7}, 0);
        TreeNode expected1 = constructTree(new Integer[]{5,4,6,2,null,null,7}, 0);
        // We can only check if the root is the same after deletion, as there can be multiple valid answers
        assert deleter.deleteNode(root1, 3).val == expected1.val : "Test case 1 failed";

        TreeNode root2 = constructTree(new Integer[]{5,3,6,2,4,null,7}, 0);
        TreeNode expected2 = constructTree(new Integer[]{5,3,6,2,4,null,7}, 0);
        assert deleter.deleteNode(root2, 0).val == expected2.val : "Test case 2 failed";

        assert deleter.deleteNode(null, 0) == null : "Test case 3 failed";

        System.out.println("All test cases passed!");
    }
}
