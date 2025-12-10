package implementations;

import java.io.Serializable;

/**
 * Represents a single node within the Binary Search Tree (BST).
 * Each node holds a data element and references to its left and right child nodes.
 * This class implements Serializable to allow the entire BST structure to be persistent.
 * * @param <E> The type of data element stored in the node.
 * @author Precious, Monica, Jasmine, Mitali
 */
public class BSTreeNode<E> implements Serializable
{
	private static final long serialVersionUID = 1L; // Unique ID for serialization compatibility.
	
	private E data; // The actual data element stored in this node (e.g., a Word object).
	private BSTreeNode<E> left; // Reference to the left child node.
	private BSTreeNode<E> right; // Reference to the right child node.
	
	/**
	 * Constructor for BSTreeNode.
	 * Initializes the node with data and sets both child pointers to null.
	 * @param data The element to store in this node.
	 */
	public BSTreeNode(E data) 
	{
		this.data = data;
		this.left = null; // New nodes start with no left child.
		this.right = null; // New nodes start with no right child.
	}

	/**
	 * Retrieves the data element stored in this node.
	 * @return The data element (E).
	 */
	public E getData() {
		return data;
	}
	
	/**
	 * Retrieves the data element stored in this node (alias for getData()).
	 * @return The data element (E).
	 */
	public E getElement() {
		return data;
	}

	/**
	 * Sets a new data element for this node.
	 * @param data The new data element.
	 */
	public void setData(E data) {
		this.data = data;
	}

	/**
	 * Retrieves the left child node.
	 * @return The BSTreeNode object representing the left child, or null if none exists.
	 */
	public BSTreeNode<E> getLeft() {
		return left;
	}

	/**
	 * Sets the left child pointer.
	 * @param left The node to be set as the left child.
	 */
	public void setLeft(BSTreeNode<E> left) {
		this.left = left;
	}

	/**
	 * Retrieves the right child node.
	 * @return The BSTreeNode object representing the right child, or null if none exists.
	 */
	public BSTreeNode<E> getRight() {
		return right;
	}

	/**
	 * Sets the right child pointer.
	 * @param right The node to be set as the right child.
	 */
	public void setRight(BSTreeNode<E> right) {
		this.right = right;
	}
	

	
}