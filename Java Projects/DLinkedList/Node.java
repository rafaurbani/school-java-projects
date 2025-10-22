
public class Node<E> {

	private E element;
	private Node<E> next;
	private Node<E> prev;
	
	public Node() {
		this(null, null, null);
	}
	
	public Node(E e) {
		this(e, null, null);
	}
	
	public Node(E element, Node<E> next, Node<E> prev) {
		this.element = element;
		this.next = next;
		this.prev = prev;
	}

	public E getElement() {
		return element; 
	}

	public Node<E> getPrevious() { 
		return prev;
	}

	public Node<E> getNext() { 
		return next;
	}

	public void setElement(E e) { 
		element = e; 
	}

	public void setPrevious(Node<E> p) {
		prev = p; 
	}

	public void setNext(Node<E> n) {
		next = n; 
	}
	
}
