package implementations;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import utilities.Iterator;

/**
 * A simple iterator implementation used to traverse lists of elements
 * produced by Binary Search Tree traversals. This iterator steps through
 * elements stored in an ArrayList in sequential order.
 *
 *
 * @param <E> The type of elements returned by this iterator.
 * 
 * author: Precious, Monica, Jasmine, Mitali
 */

public class MyIterator<E> implements Iterator<E>
{
	
	private int currentIndex = 0;
	private ArrayList<E> data;
	
	public MyIterator(ArrayList<E> data)
	{
		this.data = data;
	}

	@Override
	public boolean hasNext() {
		
		return currentIndex < data.size();
	}

	@Override
	@SuppressWarnings("unchecked")
	public E next() throws NoSuchElementException {
		if(!hasNext()) throw new NoSuchElementException("No more elements");
		// Return current and increment index
		return (E) data.get(currentIndex++);
	}

}