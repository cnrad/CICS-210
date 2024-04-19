/*
 * Copyright 2023 Marc Liberatore.
 */
package trees;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A collection of utility methods for trees and their nodes.
 * 
 * You will almost certainly need to add some methods here to complete
 * the unimplemented methods!
 */
public class TreeUtilities {
    /**
     * Perform an in-order traversal of the tree rooted at the given node, and
     * return
     * a list of the elements in the order they were visited.
     * 
     * @param node
     * @return a list of elements from the tree from an in-order traversal starting
     *         at node
     */
    static <E> List<E> inOrder(Node<E> node) {
        List<E> list = new ArrayList<>();

        if (node == null) {
            return list;
        }

        // Left (smaller)
        if (node.left != null)
            inOrderHelper(node.left, list);

        // Current
        list.add(node.data);

        // Right (larger)
        if (node.right != null)
            inOrderHelper(node.right, list);

        return list;
    }

    // Helper method
    static <E> void inOrderHelper(Node<E> node, List<E> list) {
        if (node == null)
            return;

        if (node.left != null)
            inOrderHelper(node.left, list);
        list.add(node.data);
        if (node.right != null)
            inOrderHelper(node.right, list);

        return;
    }

    /**
     * Returns the height of the node n.
     * 
     * null has a height of -1; otherwise, the height is defined as
     * 1 + the height of the larger of the node's two subtrees.
     * 
     * @param n
     * @return the height of the node n
     */
    static <E> int height(Node<E> n) {
        if (n == null)
            return -1;

        return 1 + Math.max(height(n.left), height(n.right));
    }

    /**
     * Return a new, balanced tree containing all the values of the old tree bst.
     * 
     * @param bst
     * @return a new, balanced tree containing all the values of the old tree bst
     */
    static <E extends Comparable<E>> BinarySearchTree<E> intoBalanced(BinarySearchTree<E> bst) {
        List<E> values = inOrder(bst.root);
        BinarySearchTree<E> newTree = new BinarySearchTree<E>();

        if (bst.root == null)
            return newTree;

        // Sort the values
        Collections.sort(values);

        // Add the rest
        addToTree(newTree, values, 0, values.size() - 1);

        return newTree;
    }

    // Helper method
    static <E extends Comparable<E>> void addToTree(BinarySearchTree<E> bst, List<E> values, int start, int end) {
        // No element exists
        if (start > end) {
            return;
        }

        int mid = (start + end) / 2;
        bst.add(values.get(mid));

        addToTree(bst, values, start, mid - 1);
        addToTree(bst, values, mid + 1, end);
    }

    /**
     * Returns true iff the tree rooted at n is a Binary Search Tree.
     * 
     * It must have no more than two children per node.
     * 
     * Each node's value must be greater than all the values in its left subtree,
     * and smaller
     * than all the values in its right subtree. (This implies duplicate values are
     * not allowed.)
     * 
     * @param n true iff the tree rooted at n is a Binary Search Tree
     * @return
     */
    static <E extends Comparable<E>> boolean isBST(Node<E> n) {
        if (n == null)
            return true;

        // Check left and right children compared to current
        if (n.left != null && n.left.data.compareTo(n.data) > 0)
            return false;
        if (n.right != null && n.right.data.compareTo(n.data) < 0)
            return false;

        if (!isBST(n.left) || !isBST(n.right))
            return false;

        return true;
    }

    /**
     * Returns true iff the tree rooted at n is an AVL tree.
     * 
     * AVL trees are Binary Search trees with the additional property that
     * every node in the tree has the AVL property.
     * 
     * A node has the AVL property iff the height of its left subtree and the
     * height of its right subtree differ by no more than 1.
     * 
     * @param <E>
     * @param n
     * @returntrue iff the tree rooted at n is an AVL tree
     */
    static <E extends Comparable<E>> boolean isAVLTree(Node<E> n) {
        if (n == null || n.left == null && n.right == null)
            return true;

        return Math.abs(height(n.left) - height(n.right)) <= 1;
    }

    /**
     * Returns true iff the subtrees rooted at n and m have the same values
     * and same structure.
     * 
     * Only checks child references, not parent references.
     * 
     * @param n
     * @param m
     * @return true iff the subtrees rooted at n and m have the same values and same
     *         structure
     */
    static <E> boolean equalSubtrees(Node<E> n, Node<E> m) {
        if (n == null && m == null) {
            return true;
        } else if (n == null || m == null) {
            return false;
        } else {
            return n.data.equals(m.data) && equalSubtrees(n.left, m.left) && equalSubtrees(n.right, m.right);
        }
    }
}
