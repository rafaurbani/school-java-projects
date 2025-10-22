
public class LinkedStack<E> extends DLinkedList<E> implements Stack<E> {
	
	public LinkedStack() {
		super();
	}
	
	public int numElements() {
		return size;
	}

	public void push(E element) throws OverflowException {
		add(element);
	}
	
	public E pop() throws UnderflowException {
		return remove(0);
	}

	
	public E top() throws UnderflowException {
		return head.getElement();
	}
	
	public String toString() {
		String s = "";

		if (isEmpty()) {
			return "Pilha vazia";
		}

		s += "Pilha: \n";
		NodeClass<E> node = head;

		while (node != null) {
			s += node.getElement().toString() + "\n";
			node = node.getNext();
		}

		return s;
	}

}
