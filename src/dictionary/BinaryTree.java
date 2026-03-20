package dictionary;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @param <E>
 */
public class BinaryTree<E extends Comparable<E>> {

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

    private Node<E> root;
    private int size;

    public BinaryTree() {
        root = null;
        size = 0;
    }

    /**
     * 
     * @param element
     */
    public void insert(E element) {
        root = insertRecursive(root, element);
    }

    /**
     * @param element
     * @return
     */
    public E search(E element) {
        return searchRecursive(root, element);
    }

    /**
     * @param element the element to look for
     */
    public boolean contains(E element) {
        return search(element) != null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 
     * @return list of elements in ascending order
     */
    public List<E> inOrder() {
        List<E> result = new ArrayList<>();
        inOrderRecursive(root, result);
        return result;
    }

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
