package implementations;

public class BSTreeNode<E>
{
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
