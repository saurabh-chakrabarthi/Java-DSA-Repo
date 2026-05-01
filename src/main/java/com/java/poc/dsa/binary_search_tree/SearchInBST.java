package com.java.poc.dsa.binary_search_tree;

import com.java.poc.dsa.binaryTree.TreeNode;

public class SearchInBST {

    public static void main(String[] args) {
        TreeNode treenode = new TreeNode(24);
        treenode.left = new TreeNode(16);
        treenode.right = new TreeNode(30);
        treenode.left.left = new TreeNode(13);
        treenode.left.right = new TreeNode(20);
        treenode.right.left = new TreeNode(25);
        System.out.println(treenode);
        System.out.println(searchBST(treenode, 30));
    }

    public static TreeNode searchBST(TreeNode root, int val) {
        TreeNode ans;
        if(root==null||root.val==val){
            return root;
        }
        if(root.val>val){
            ans = searchBST(root.left,val);
        }else{
            ans = searchBST(root.right,val);
        }

        return ans;
    }
}

