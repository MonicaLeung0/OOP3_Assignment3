package implementations;

import java.io.Serializable;
import java.util.ArrayList;

import utilities.BSTreeADT;

import utilities.Iterator;


/**
 * Implementation of a Binary Search Tree (BST) based on the BSTreeADT interface.
 * It stores elements that are Comparable, ensuring correct sorting order.
 * This class also implements Serializable to allow the entire tree structure to be saved and restored.
 *
 * @param <E> The type of element stored in the tree, which must extend Comparable.
 * @author Precious, Monica, Jasmine, Mitali
 */

public class BSTree<E extends Comparable<? super E>> implements BSTreeADT<E>, Serializable 
{
	private static final long serialVersionUID = 1L;
	
	public BSTreeNode<E> root; // The top node of the tree.
	private int size; // Tracks the total number of elements in the tree.
	
	
	/**
	 * Default constructor for an empty BST.
	 */
	public BSTree()
	{
		this.root = null;
		this.size = 0;
	}
	
	/**
	 * Constructor for a BST initialized with a single root entry.
	 * @param newEntry The initial element for the root node.
	 */
	public BSTree(E newEntry)
	{
		BSTreeNode<E> newRoot = new BSTreeNode<E>(newEntry);
		this.root = newRoot;
		this.size = 1;
	}

	/**
	 * Retrieves the root node of the tree.
	 * @return The root node.
	 * @throws NullPointerException if the tree is empty.
	 */
	@Override
	public BSTreeNode<E> getRoot() throws NullPointerException {
		if(root == null) 
		{
			// Check if the tree is empty before returning the root.
			throw new NullPointerException("the tree is empty");
			
		}
		return root;
	}

	/**
	 * Determines the height of the tree (number of levels - 1).
	 * @return The height of the tree.
	 */
	@Override
	public int getHeight() {
		
		// Calls recursive helper function and adds 1 to account for the root level (height is depth - 1).
		return heightRecursive(this.root)+1;

	}
	
	/**
	 * Recursive helper method to calculate the height of a subtree.
	 * The height of a node is 1 plus the height of the taller of its two children.
	 * @param node The current node being examined.
	 * @return The height of the subtree rooted at 'node' (-1 for an empty tree).
	 */
	private int heightRecursive(BSTreeNode<E> node) {
	    if (node == null) return -1; // Base case: height of an empty subtree is -1.

	    // Recursively find the height of the left and right subtrees.
	    int leftHeight = heightRecursive(node.getLeft());
	    int rightHeight = heightRecursive(node.getRight());

	    // Height is 1 + the maximum height of its children.
	    return 1 + Math.max(leftHeight, rightHeight);
	}

	/**
	 * Returns the number of elements currently stored in the tree.
	 * @return The size of the tree.
	 */
	@Override
	public int size() {
		return this.size;
	}
	
	/**
	 * Checks if the tree contains any elements.
	 * @return true if the root is null, indicating an empty tree.
	 */
	@Override
	public boolean isEmpty() {
		
		return root==null;
	}

	/**
	 * Clears the tree by setting the root to null and size to zero.
	 */
	@Override
	public void clear() {
		this.root = null;
		this.size = 0;
		
	}

	/**
	 * Checks if the tree contains a specific entry by calling the search method.
	 * @param entry The element to check for.
	 * @return true if the element is found, false otherwise.
	 * @throws NullPointerException if the entry is null.
	 */
	@Override
	public boolean contains(E entry) throws NullPointerException {
		return search(entry) != null;
	}
	
	
	/**
	 * Searches for a node containing the specified entry.
	 * @param entry The element object being searched.
	 * @return A new node containing the found element's data, or null if not found.
	 * @throws NullPointerException if the entry being passed in is null.
	 */
	@Override
	public BSTreeNode<E> search(E entry) throws NullPointerException {
		if(entry == null)
		{
			throw new NullPointerException("Entry cannot be null.");
		}
		if (this.root == null) 
		{
			return null; // Tree is empty.
		} 
		
		BSTreeNode<E> node = this.root;
		
		// Iterative search traversal, starting at the root.
		while(node!=null) 
		{
			int comparison = entry.compareTo(node.getData());
			
			if(comparison == 0) 
			{
				// Found the element. Returns a new node with the data (shallow copy of data).
				return new BSTreeNode<E>(node.getData()); 
			}
			else if(comparison > 0 ) 
			{
				// Entry is greater than current node's data, move to the right subtree.
				node = node.getRight();
			}
			else // comparison < 0
			{
				// Entry is less than current node's data, move to the left subtree.
				node = node.getLeft();
			}			
		}
		
		return null; // Not found.
	}
	
	/**
	 * Adds a new entry to the tree in its correct sorted position.
	 * @param newEntry The element being added to the tree.
	 * @return true if the element is added successfully.
	 * @throws NullPointerException if the entry is null.
	 */

	@Override
	public boolean add(E newEntry) throws NullPointerException {
		if(newEntry == null)
		{
			throw new NullPointerException("Entry cannot be null.");
		}
		this.size ++; // Optimistic increment. Decremented if insertion fails.
		
		// Case 1: Empty tree. New entry becomes the root.
		if (this.root == null) 
		{
			BSTreeNode<E> newRoot = new BSTreeNode<E>(newEntry);
			this.root = newRoot;
			return true;
		} 
		
		BSTreeNode<E> node = this.root;
		
		// Iteratively find the correct insertion point.
		while(node!=null) 
		{
			// Go left for less than or equal to (handles duplicates by placing them on the left).
			if(newEntry.compareTo(node.getData()) <= 0) 
			{
				if(node.getLeft()==null) 
				{
					// Found insertion point: insert new node as the left child.
					BSTreeNode<E> newNode = new BSTreeNode<E>(newEntry);
					node.setLeft(newNode);
					return true;
				}
				else 
				{
					node = node.getLeft(); // Continue left traversal.
				}
			}
			else // Go right for greater than.
			{
				if(node.getRight()==null) 
				{
					// Found insertion point: insert new node as the right child.
					BSTreeNode<E> newNode = new BSTreeNode<E>(newEntry);
					node.setRight(newNode);
					return true;
				}
				else 
				{
					node = node.getRight(); // Continue right traversal.
				}
			}
		
		}
		
		// Should only be reached if insertion logic failed.
		this.size --; 
		return false;
	}

	/**
	 * Removes the smallest element in the tree (the leftmost node).
	 * @return The removed node, or null if the tree is empty.
	 */
	@Override
	public BSTreeNode<E> removeMin() {
		BSTreeNode<E> node = this.root;
		BSTreeNode<E> preNode = null; // Used to track the parent of 'node'.
		
		if(root==null) 
		{
			return null;
		}
		
		// Find the leftmost node (the minimum).
		while(node.getLeft() != null)
		{
			preNode = node;
			node = node.getLeft();
		}
		
		this.size --;
		
		// Case 1: The root is the minimum (preNode is null).
		if(preNode == null) 
		{
			// The new root becomes the old root's right child (if it exists).
			this.root = root.getRight(); 
		} 
		// Case 2: The minimum is a descendant.
		else 
		{
			// Link the parent's left pointer to the minimum's right child (if it exists).
			preNode.setLeft(node.getRight());
		}
		
		// Isolate the removed node for return.
		node.setLeft(null);
		node.setRight(null);
		return node;
	}


	/**
	 * Removes the largest element in the tree (the rightmost node).
	 * @return The removed node, or null if the tree is empty.
	 */
	@Override
	public BSTreeNode<E> removeMax() {
		BSTreeNode<E> node = this.root;
		BSTreeNode<E> preNode = null; // Used to track the parent of 'node'.
		
		if(root==null) 
		{
			return null;
		}
		
		// Find the rightmost node (the maximum).
		while(node.getRight() != null)
		{
			preNode = node;
			node = node.getRight();
		}
		
		this.size --;
		
		// Case 1: The root is the maximum (preNode is null).
		if(preNode == null) 
		{
			// The new root becomes the old root's left child (if it exists).
			this.root = root.getLeft(); 
		} 
		// Case 2: The maximum is a descendant.
		else 
		{
			// Link the parent's right pointer to the maximum's left child (if it exists).
			preNode.setRight(node.getLeft());
		}
		
		// Isolate the removed node for return.
		node.setLeft(null);
		node.setRight(null);
		return node;
	}


	/**
	 * Generates an in-order iteration over the contents of the tree.
	 * Traversal order: Left -> Root -> Right (results in sorted/alphabetical order).
	 * Uses an iterative approach with a stack.
	 * @return An iterator with the elements in the natural order.
	 */
	@Override
	public Iterator<E> inorderIterator() {
		ArrayList<E> result = new ArrayList<E>(); // List to hold the sorted traversal order.
		ArrayList<BSTreeNode<E>> stack = new ArrayList<BSTreeNode<E>>(); // Stack for iterative traversal.
		BSTreeNode<E> current = root;

	    while (current != null || !stack.isEmpty()) {

	        // 1. Travel down to the leftmost node, storing all parent nodes on the stack.
	        while (current != null) {
	            stack.add(current);
	            current = current.getLeft();
	        }

	        // 2. The leftmost node (or next node in order) is at the top of the stack.
	        current = stack.get(stack.size()-1);
	        stack.remove(stack.size()-1);
	        result.add(current.getData());  // Visit (add to list)
	        
	        // 3. Move to the right child to process the right subtree.
	        current = current.getRight();
	    }

	    return new MyIterator<E>(result);
	}

	/**
	 * Generates a pre-order iteration over the contents of the tree.
	 * Traversal order: Root -> Left -> Right.
	 * Uses an iterative approach with a stack.
	 * @return An iterator with the elements in pre-order.
	 */
	@Override
	public Iterator<E> preorderIterator() {
		ArrayList<E> result = new ArrayList<>();

	    if (root == null)
	    {
	    	return new MyIterator<E>(new ArrayList<E>());
	    }

	    ArrayList<BSTreeNode<E>> stack = new ArrayList<BSTreeNode<E>>();
	    stack.add(root);

	    // Iterative Pre-order Traversal
	    while (!stack.isEmpty()) {
	    	BSTreeNode<E> node = stack.get(stack.size()-1);
	    	stack.remove(stack.size()-1);
	        result.add(node.getData()); // 1. Visit (add to list)

	        // 2 & 3. Process Right then Left (push Right first, so Left is popped and processed next due to LIFO).
	        if (node.getRight() != null) stack.add(node.getRight());
	        if (node.getLeft() != null) stack.add(node.getLeft());
	    }

	    return new MyIterator<E>(result);
	}

	/**
	 * Generates a post-order iteration over the contents of the tree.
	 * Traversal order: Left -> Right -> Root.
	 * Uses an iterative approach with two stacks to achieve the correct L-R-R order.
	 * @return An iterator with the elements in post-order.
	 */
	@Override
	public Iterator<E> postorderIterator() {
		ArrayList<E> result = new ArrayList<>();

	    if (root == null) return new MyIterator<E>(new ArrayList<E>());

	    ArrayList<BSTreeNode<E>> stack1 = new ArrayList<BSTreeNode<E>>(); // Used to process nodes (R-R-L order)
	    ArrayList<BSTreeNode<E>> stack2 = new ArrayList<BSTreeNode<E>>(); // Used to store results (L-R-R order)

	    stack1.add(root);

	    // First stage: Fill stack2 in the reverse post-order (Root -> Right -> Left).
	    while (!stack1.isEmpty()) {
	    	BSTreeNode<E> node = stack1.get(stack1.size()-1);
	    	stack1.remove(stack1.size()-1);
	        stack2.add(node);

	        // Push Left child, then Right child (ensures R is processed before L for the R-R-L stack2 order).
	        if (node.getLeft() != null) stack1.add(node.getLeft());
	        if (node.getRight() != null) stack1.add(node.getRight());
	    }

	    // Second stage: Pop elements from stack2. This reverses the R-R-L order to Post-order (Left -> Right -> Root).
	    while (!stack2.isEmpty()) {
	    	
	        result.add(stack2.get(stack2.size()-1).getData());
	        stack2.remove(stack2.size()-1);
	    }

	    return new MyIterator<E>(result);
	}
	

}