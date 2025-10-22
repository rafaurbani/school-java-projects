
public class LinkedDeque<E> extends LinkedQueue<E> implements Deque<E> {

	public LinkedDeque() {
		super();
	}
	
	public void push(E element) throws OverflowException {
		add(element);
	}
	
	public E pop() throws UnderflowException {
		return remove(0);
	}

	public E top() throws UnderflowException {
		return tail.getElement();
	}
}
