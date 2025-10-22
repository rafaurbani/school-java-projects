
public class DLinkedList<E> implements List<E> {

	protected Node<E> head;
	protected Node<E> tail;
	protected int size;
	protected int maxSize;

	public DLinkedList(int max) {
		head = tail = null;
		size = 0;
		maxSize = max;
	}

	public DLinkedList() {
		this(-1);
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public boolean isFull() {
		if (maxSize == -1 || !(size == maxSize))
			return false;
		else {
			return true;
		}
	}

	public void insert(E element, int pos) throws IndexOutOfBoundsException, OverflowException {
		if (pos > size || pos < 0)
			throw new IndexOutOfBoundsException();
		else if (pos == 0) 
			add(element);
		else {
			Node<E> newNode = new Node<E>(element);
			Node<E> current = head;

			for (int i = 0; i < pos - 1; i++) {
				current = current.getNext();
			}

			newNode.setNext(current.getNext());
			if (pos < size)
				newNode.getNext().setPrevious(newNode);
			newNode.setPrevious(current);
			current.setNext(newNode);
			
			if (pos == size)
				tail = newNode;

			size++;
		}
	}

	public E remove(int pos) throws UnderflowException {
		if (size == 0)
			throw new UnderflowException();
		if (size == 1)
			clear();

		E element;
		
		if (pos == size - 1) {// se for tail
			element = tail.getElement();
			tail = tail.getPrevious();
			tail.setNext(null);
			size--;
		} else if (pos == 0) {// se for head
			element = head.getElement();
			head = head.getNext();
			head.setPrevious(null);
			size--;
		} else {
	
			Node<E> current = head;
	
			for (int i = 0; i < pos - 1; i++) {
				current = current.getNext();
			}
	
			Node<E> removed = current.getNext();

			current.setNext(removed.getNext());
			current.getNext().setPrevious(current);
	
			size--;
			element = removed.getElement();
		}
		
		return element;
	}

	public E get(int pos) {
		Node<E> current = head;

		for (int i = 0; i < pos; i++) {
			current = current.getNext();
		}

		return current.getElement();
	}

	public int search(E element) {
		int pos = 0;
		Node<E> current = head;

		while (current != null) {
			if (element.equals(current.getElement()))
				return pos;

			pos++;
			current = current.getNext();
		}

		return -1;
	}

	public void add(E element) throws OverflowException {
		if (isFull())
			throw new OverflowException();

		Node<E> newNode = new Node<E>(element);
		
		if (size > 0)
			head.setPrevious(newNode);

		newNode.setNext(head);
		head = newNode;
		size++;

		if (size == 1)
			tail = head;
	}

	public void clear() {
		head = null;
		tail = null;
		size = 0;
	}

	public void trocaNodes() {
		Node<E> current;
		Node<E> aux;
		E aux2;

		if (size >= 2) {
			current = head;
			aux = current.getNext();

			aux2 = current.getElement();
			current.setElement(aux.getElement());
			aux.setElement(aux2);

			for (int i = 1; i < size / 2; i++) {
				current = current.getNext().getNext();
				aux = aux.getNext().getNext();

				aux2 = current.getElement();
				current.setElement(aux.getElement());
				aux.setElement(aux2);
			}
		}
	}

	public void inverteLista() {
		Node<E> first = head;
		Node<E> last = tail;

		for (int i = 0; i < size / 2; i++) {
			E aux = first.getElement();
			first.setElement(last.getElement());
			last.setElement(aux);

			first = first.getNext();
			last = last.getPrevious();
		}
	}

	public void removeNodes(int pos, int quantosNodes) {
		Node<E> current = head;

		for (int i = 0; i < pos - 1; i++) {
			current = current.getNext();
		}

		Node<E> nextFromRemoved = current;

		for (int i = 0; i <= quantosNodes; i++) {
			nextFromRemoved = nextFromRemoved.getNext();
		}

		current.setNext(nextFromRemoved);
		nextFromRemoved.setPrevious(current);
		size = size - quantosNodes;
	}

	public void insereLista(DLinkedList<E> outraLista, int pos) throws OverflowException {
		if (isFull() || size + outraLista.size > maxSize)
			throw new OverflowException();

		Node<E> current = head;

		for (int i = 0; i < pos - 1; i++) {
			current = current.getNext();
		}

		if (outraLista.size == 0)
			return;

		size = size + outraLista.size;

		Node<E> nextFromList = current.getNext();
		current.setNext(outraLista.head);
		current.getNext().setPrevious(current);

		nextFromList.setPrevious(outraLista.tail);
		nextFromList.getPrevious().setNext(nextFromList);

	}

	public String toString() {
		String s = "";

		if (isEmpty()) {
			return "Lista vazia";
		}

		s += "Lista: \n";
		Node<E> node = head;

		while (node != null) {
			s += node.getElement().toString() + "\n";
			node = node.getNext();
		}

		return s;
	}

	public void setMaxSize(int max) {
		this.maxSize = max;
	}
}
