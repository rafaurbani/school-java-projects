
public class LinkedQueue<E> extends DLinkedList<E> implements Queue<E> {

	public LinkedQueue() {
		super();
	}

	public LinkedQueue(int max) {
		super(max);
	}

	public int numElements() {
		return size;
	}

	public E front() throws UnderflowException {
		return head.getElement();
	}

	public E back() throws UnderflowException {
		return tail.getElement();
	}

	public void enqueue(E element) throws OverflowException {
		insert(element, size);
	}

	public E dequeue() throws UnderflowException {
		return remove(0);
	}
	
	public String toString() {
		String s = "";

		if (isEmpty()) {
			return "Fila vazia";
		}

		s += "Fila: \n";
		NodeClass<E> node = head;

		while (node != null) {
			s += node.getElement().toString() + "\n";
			node = node.getNext();
		}

		return s;
	}

}
