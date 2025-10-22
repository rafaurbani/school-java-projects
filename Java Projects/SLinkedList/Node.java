
public class Node<E>{
	private E element;
	private NodeClass<E> next;
	
	public Node() {
		this(null, null);
	}
	
	public Node(E e) {
		this(e, null);
	}
	
	public Node(E e, NodeClass<E> n) {
		this.element = e;
		this.next = n;
	}

	public E getElement() {
		return element;
	}

	public void setElement(E element) {
		this.element = element;
	}

	public NodeClass<E> getNext() {
		return next;
	}

	public void setNext(NodeClass<E> next) {
		this.next = next;
	}  
}
