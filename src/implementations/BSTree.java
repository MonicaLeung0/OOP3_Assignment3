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
	
	public BSTreeNode<E> root;
	private int size;
	
	
	public BSTree()
	{
		this.root = null;
		this.size = 0;
	}
	public BSTree(E newEntry)
	{
		BSTreeNode<E> newRoot = new BSTreeNode<E>(newEntry);
		this.root = newRoot;
		this.size = 1;
	}

	@Override
	public BSTreeNode<E> getRoot() throws NullPointerException {
		if(root == null) 
		{
			throw new NullPointerException("the tree is empty");
			
		}
		return root;
	}

	@Override
	public int getHeight() {
		
		return heightRecursive(this.root)+1;

	}
	
	/**
	 * Recursive helper method to calculate the height of a subtree.
	 * The height of a node is 1 plus the height of the taller of its two children.
	 * @param node The current node being examined.
	 * @return The height of the subtree rooted at 'node' (-1 for an empty tree).
	 */
	private int heightRecursive(BSTreeNode<E> node) {
	    if (node == null) return -1; // empty subtree

	    int leftHeight = heightRecursive(node.getLeft());
	    int rightHeight = heightRecursive(node.getRight());

	    return 1 + Math.max(leftHeight, rightHeight);
	}

	@Override
	public int size() {
		return this.size;
	}
	@Override
	public boolean isEmpty() {
		
		return root==null;
	}

	@Override
	public void clear() {
		this.root = null;
		this.size = 0;
		
	}

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
			throw new NullPointerException();
		}
		if (this.root == null) 
		{
			return null;
		} 
		
		BSTreeNode<E> node = this.root;
		
		while(node!=null) 
		{
			if(entry.compareTo(node.getData()) == 0) 
			{
				return new BSTreeNode<E>(node.getData());
			}
			else if(entry.compareTo(node.getData()) > 0 ) 
			{
				node = node.getRight();
			}
			else
			{
				node = node.getLeft();
			}			
		}
		
		return null;
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
			throw new NullPointerException();
		}
		this.size ++;
		if (this.root == null) 
		{
			BSTreeNode<E> newRoot = new BSTreeNode<E>(newEntry);
			this.root = newRoot;
			return true;
		} 
		
		BSTreeNode<E> node = this.root;
		
		while(node!=null) 
		{
			if(newEntry.compareTo(node.getData()) <= 0) 
			{
				if(node.getLeft()==null) 
				{
					
					BSTreeNode<E> newNode = new BSTreeNode<E>(newEntry);
					node.setLeft(newNode);
					return true;
				}
				else 
				{
					node = node.getLeft();
				}
				
				
			}
			else
			{
				if(node.getRight()==null) 
				{
					BSTreeNode<E> newNode = new BSTreeNode<E>(newEntry);
					node.setRight(newNode);
					return true;
				}
				else 
				{
					node = node.getRight();
				}
			}
		
		}
		
		this.size --;
		return false;
	}

	@Override
	public BSTreeNode<E> removeMin() {
		BSTreeNode<E> node = this.root;
		BSTreeNode<E> preNode = this.root;
		if(root==null) 
		{
			return null;
		}
		this.size --;
		if(root.getLeft() == null && root.getRight() == null ) 
		{
			this.root = null;
			return node;
		}
		else if(root.getLeft() == null && root.getRight() != null)
		{
			this.root  = this.root.getRight();
			node.setLeft(null);
			node.setRight(null);
			return node;
			
		}
		else 
		{
			while(node!=null) 
			{
				if(node.getLeft()==null) 
				{
					preNode.setLeft(null);
					return node;
				}
				else 
				{
					
					preNode = node;
					node = node.getLeft();
					
				}
			}
		}
		
		this.size ++;
		return null;
	}

	@Override
	public BSTreeNode<E> removeMax() {
		BSTreeNode<E> node = this.root;
		BSTreeNode<E> preNode = this.root;
		if(root==null) 
		{
			return null;
		}
		this.size --;
		if(root.getLeft() == null && root.getRight() == null ) 
		{
			this.root = null;
			return node;
		}
		else if(root.getRight() == null && root.getLeft() != null)
		{
			this.root  = this.root.getLeft();
			node.setLeft(null);
			node.setRight(null);
			return node;
			
		}
		else 
		{
			while(node!=null) 
			{
				if(node.getRight()==null) 
				{
					preNode.setRight(null);
					return node;
				}
				else 
				{
					
					preNode = node;
					node = node.getRight();
					
				}
			}
		}
		
		this.size ++;
		return null;
	}

	@Override
	public Iterator<E> inorderIterator() {
		ArrayList<E> result = new ArrayList<E>();
		ArrayList<BSTreeNode<E>> stack = new ArrayList<BSTreeNode<E>>();
		BSTreeNode<E> current = root;

	    while (current != null || !stack.isEmpty()) {

	        while (current != null) {
	            stack.add(current);
	            current = current.getLeft();
	        }

	        
	        current = stack.get(stack.size()-1);
	        stack.remove(stack.size()-1);
	        result.add(current.getData());  // add visited node to list
	        
	        current = current.getRight();
	    }

	    return new MyIterator<E>(result);
	}

	@Override
	public Iterator<E> preorderIterator() {
		ArrayList<E> result = new ArrayList<>();

	    if (root == null)
	    {
	    	return new MyIterator<E>(new ArrayList<E>());
	    }

	    ArrayList<BSTreeNode<E>> stack = new ArrayList<BSTreeNode<E>>();
	    stack.add(root);

	    while (!stack.isEmpty()) {
	    	BSTreeNode<E> node = stack.get(stack.size()-1);
	    	stack.remove(stack.size()-1);
	        result.add(node.getData());

	        // **Right first**, so left is processed earlier
	        if (node.getRight() != null) stack.add(node.getRight());
	        if (node.getLeft() != null) stack.add(node.getLeft());
	    }

	    return new MyIterator<E>(result);
	}

	@Override
	public Iterator<E> postorderIterator() {
		ArrayList<E> result = new ArrayList<>();

	    if (root == null) return new MyIterator<E>(new ArrayList<E>());

	    ArrayList<BSTreeNode<E>> stack1 = new ArrayList<BSTreeNode<E>>();
	    ArrayList<BSTreeNode<E>> stack2 = new ArrayList<BSTreeNode<E>>();

	    stack1.add(root);

	    while (!stack1.isEmpty()) {
	    	BSTreeNode<E> node = stack1.get(stack1.size()-1);
	    	stack1.remove(stack1.size()-1);
	        stack2.add(node);

	        if (node.getLeft() != null) stack1.add(node.getLeft());
	        if (node.getRight() != null) stack1.add(node.getRight());
	    }

	    // Now reverse order from stack2
	    while (!stack2.isEmpty()) {
	    	
	        result.add(stack2.get(stack2.size()-1).getData());
	        stack2.remove(stack2.size()-1);
	    }

	    return new MyIterator<E>(result);
	}
	

}
