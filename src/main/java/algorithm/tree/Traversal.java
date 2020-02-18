package algorithm.tree;

import algorithm.utils.TreeNode;
import algorithm.utils.TreeUtils;

import java.util.LinkedList;

public class Traversal {
    public static void preOrder(TreeNode root) {
        LinkedList<TreeNode> stack = new LinkedList<>();
        if (root == null) {
            return;
        }
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                System.out.print(root + " ");
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            root = root.right;
        }
    }

    public static void inOrder(TreeNode root) {
        LinkedList<TreeNode> stack = new LinkedList<>();
        if (root == null) {
            return;
        }
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            System.out.print(root + " ");
            root = root.right;
        }
    }

    public static void postOrder(TreeNode root) {
        LinkedList<TreeNode> stack = new LinkedList<>();
        TreeNode lastVisit = null;
        if (root == null) {
            return;
        }
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            if (root.right == null || root.right == lastVisit) {
                System.out.print(root + " ");
                lastVisit = root;
                root = null;
            } else {
                stack.push(root);
                root = root.right;
            }
        }
    }

    public static void main(String[] args) {
        Integer[] arr = {1,2,4,null,3,null,6};
        TreeNode root = TreeUtils.buildTreeFromArray(arr);
        preOrder(root);
        System.out.println();
        inOrder(root);
        System.out.println();
        postOrder(root);
    }
}
