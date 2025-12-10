package implementations;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import utilities.Iterator;

/**

 * Custom implementation of the Iterator interface.
 * This iterator is designed to provide a mono-directional traversal over a collection
 * of elements, typically generated from a BST traversal (like in-order).
 * It uses an internal ArrayList to store a snapshot of the data for safe iteration.
 *
 * @param <E> The type of element this iterator returns.
 * @author Precious, Monica, Jasmine, Mitali
 */


public class MyIterator<E> implements Iterator<E>
{
	
	private int currentIndex = 0; // Tracks the current position in the internal ArrayList.
	private ArrayList<E> data; // The list containing the snapshot of data to iterate over.
	
	/**
	 * Constructor. Initializes the iterator with the data collected from a BST traversal.
	 * This ArrayList is essentially the "deep copy" of the elements required for the iterator contract.
	 * @param data The ArrayList containing the elements to iterate over in the desired order.
	 */
	public MyIterator(ArrayList<E> data)
	{
		this.data = data;
	}

	/**
	 * Checks if the iteration has more elements.
	 * @return true if the current index is less than the total size of the data list.
	 */
	@Override
	public boolean hasNext() {
		
		return currentIndex < data.size();
	}

	/**
	 * Returns the next element in the iteration.
	 * @return The next element.
	 * @throws NoSuchElementException If the iteration has no more elements (i.e., hasNext() is false).
	 */
	@Override
	public E next() throws NoSuchElementException {
	    if(!hasNext()) throw new NoSuchElementException("No more elements in the iteration.");
	    
	    return data.get(currentIndex++); // No cast needed
	}

}