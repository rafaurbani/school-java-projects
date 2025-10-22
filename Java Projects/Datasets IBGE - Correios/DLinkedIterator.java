
public class DLinkedIterator<E> implements ListIterator<E>{
	
	private DLinkedList<E> list;
    private NodeClass<E> current;
    private int currentPos;
    
    public DLinkedIterator(DLinkedList<E> list) {
    	this.list = list;
    	this.current = list.head;
    	this.currentPos = 0;
    }
	
	public E current() {		 
		return current.getElement();
	}

	public E next() {
		current = current.getNext();
		currentPos++;
		return current.getElement();
	}

	public E previous() {
		current = current.getPrevious();
		currentPos--;
		return current.getElement();
	}

	public boolean hasNext() {
		if (current.getNext() != null)
			return true;
		else
			return false;
	}

	public boolean hasPrevious() {
		if (current.getPrevious() != null)
			return true;
		else
			return false;
	}

	public void reset() {
		 current = this.list.head;	
		 currentPos = 0;
	}

	public void addAfter(E elem) throws IndexOutOfBoundsException, OverflowException {
		list.insert(elem, (currentPos + 1));
	}

	public void addBefore(E elem) throws IndexOutOfBoundsException, OverflowException {
		list.insert(elem, currentPos);
	}

	public void remove() throws UnderflowException {
		list.remove(currentPos);
	}
}
