package implementations;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import utilities.Iterator;

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