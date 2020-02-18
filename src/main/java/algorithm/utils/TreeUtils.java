package algorithm.utils;

import java.util.LinkedList;

/**
 * Some utils of binary tree.
 * @author ShaoJiale
 * Date: 2020/2/13
 */
public abstract class TreeUtils {
    /**
     * Create a binary tree with the given array.
     * Such as [1, 2, 3, null, 4, 5, 6] would be convert into the tree below:
     *       1
     *     /   \
     *    2     3
     *     \   / \
     *      4 5   6
     * @param nodes tree nodes order by level
     * @return root of the tree
     */
    public static TreeNode buildTreeFromArray(Integer[] nodes) {
        if (nodes == null || nodes.length == 0) {
            throw new IllegalArgumentException("Tree cannot be null or empty");
        }

        TreeNode root;
        TreeNode res = null;
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.offer(new TreeNode(nodes[0]));
        int count = 1;
        int index = 1;

        while (index < nodes.length) {
            for (int i = 0; i < count; i++) {
                root = queue.poll();
                if (count == 1) res = root;
                Integer leftVal = nodes[index++];
                Integer rightVal = nodes[index++];
                TreeNode leftNode =  leftVal == null ? null : new TreeNode(leftVal);
                TreeNode rightNode = rightVal == null ? null : new TreeNode(rightVal);
                root.left = leftNode;
                root.right = rightNode;
                queue.offer(leftNode);
                queue.offer(rightNode);
            }
            count *= 2;
        }
        return res;
    }
}
