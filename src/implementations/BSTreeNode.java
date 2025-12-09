package implementations;

import java.io.Serializable;

/**
 * Represents a single node within a Binary Search Tree (BST).
 * Each node stores a data element along with references to its left
 * and right child nodes. This class implements Serializable, allowing
 * BST structures using these nodes to be saved and restored.
 *
 * @param <E> The type of data stored in this node.
 *            It can be any object type used within the tree.
 * 
 * @author Precious, Monica, Jasmine, Mitali
 */

public class BSTreeNode<E> implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private E data;
	private BSTreeNode<E> left;
	private BSTreeNode<E> right;
	
	public BSTreeNode(E data) 
	{
		this.data = data;
		this.left = null;
		this.right = null;
	}

	public E getData() {
		return data;
	}
	public E getElement() {
		return data;
	}

	public void setData(E data) {
		this.data = data;
	}

	public BSTreeNode<E> getLeft() {
		return left;
	}

	public void setLeft(BSTreeNode<E> left) {
		this.left = left;
	}

	public BSTreeNode<E> getRight() {
		return right;
	}

	public void setRight(BSTreeNode<E> right) {
		this.right = right;
	}
	

	

}
