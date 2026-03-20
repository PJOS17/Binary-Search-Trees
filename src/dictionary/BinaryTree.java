package dictionary;

import java.util.ArrayList;
import java.util.List;

/**
 * Generic Binary Search Tree (BST) implementation.
 *
 * Reference for BST structure and algorithms:
 * Bailey, D. A. (2003). Java Structures: Data Structures in Java for the Principled Programmer.
 * McGraw-Hill. Chapter 12: Binary Trees.
 * Also consulted: https://en.wikipedia.org/wiki/Binary_search_tree
 *
 * @param <E> the type of elements stored, must be Comparable
 */
public class BinaryTree<E extends Comparable<E>> {

    // -------------------------------------------------------------------------
    // Inner Node class
    // -------------------------------------------------------------------------

    private static class Node<E> {
        E data;
        Node<E> left;
        Node<E> right;

        Node(E data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    // -------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------

    private Node<E> root;
    private int size;

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    public BinaryTree() {
        root = null;
        size = 0;
    }

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

    /**
     * Inserts a new element into the BST.
     * Duplicate keys are ignored (the existing entry is kept).
     *
     * @param element the element to insert
     */
    public void insert(E element) {
        root = insertRecursive(root, element);
    }

    /**
     * Searches for an element in the BST.
     *
     * @param element the element to search for
     * @return the matching element if found, or null if not present
     */
    public E search(E element) {
        return searchRecursive(root, element);
    }

    /**
     * Returns true if the BST contains the given element.
     *
     * @param element the element to look for
     */
    public boolean contains(E element) {
        return search(element) != null;
    }

    /**
     * Returns the number of elements in the BST.
     */
    public int size() {
        return size;
    }

    /**
     * Returns true if the BST is empty.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Performs an in-order traversal (left → root → right) and returns
     * the elements as an ordered list.
     *
     * @return list of elements in ascending order
     */
    public List<E> inOrder() {
        List<E> result = new ArrayList<>();
        inOrderRecursive(root, result);
        return result;
    }

    // -------------------------------------------------------------------------
    // Private helpers
    // -------------------------------------------------------------------------

    private Node<E> insertRecursive(Node<E> node, E element) {
        if (node == null) {
            size++;
            return new Node<>(element);
        }

        int cmp = element.compareTo(node.data);

        if (cmp < 0) {
            node.left = insertRecursive(node.left, element);
        } else if (cmp > 0) {
            node.right = insertRecursive(node.right, element);
        }
        // cmp == 0 → duplicate key, keep existing entry

        return node;
    }

    private E searchRecursive(Node<E> node, E element) {
        if (node == null) {
            return null;
        }

        int cmp = element.compareTo(node.data);

        if (cmp == 0) {
            return node.data;
        } else if (cmp < 0) {
            return searchRecursive(node.left, element);
        } else {
            return searchRecursive(node.right, element);
        }
    }

    private void inOrderRecursive(Node<E> node, List<E> result) {
        if (node == null) {
            return;
        }
        inOrderRecursive(node.left, result);
        result.add(node.data);
        inOrderRecursive(node.right, result);
    }
}
