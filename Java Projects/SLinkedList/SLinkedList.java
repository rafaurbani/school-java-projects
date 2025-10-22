import java.util.ArrayList;
import java.util.Comparator;

public class SLinkedList<E> implements List<E> {
	public NodeClass<E> head;
	public NodeClass<E> tail;
	public int size;
	
	public SLinkedList() {
		head = null;
		tail = null;
		size = 0;
	}
	
	public boolean isEmpty() {
		return head == null;
	}
	
	public int size() {
		NodeClass<E> current = head;
		int cont = 0;
		
		while (current != null) {
			current = current.getNext();
			cont++;
		}
		
		return cont;
	}

	public void insert(E element, int pos) throws IndexOutOfBoundsException {
		if (pos >= size || pos < 0)
			throw new IndexOutOfBoundsException();
		if (pos == size - 1)
			add(element);
		else {
			NodeClass<E> newNode = new NodeClass<E>(element);
			NodeClass<E> current = head;
			int posAtual = 0;
			
			while (posAtual < pos) {
				current = current.getNext();
				posAtual++;
			}
			
			newNode.setNext(current.getNext());
			current.setNext(newNode);
			size++;
		}
	}

	public E remove(int pos) {
		int posAtual = 0;
		NodeClass<E> current = head;
		
		if (pos == 0) {
			head = head.getNext();
			return current.getElement();
		}
		
		while (posAtual < pos - 1) {
			current = current.getNext();
			posAtual++;
		}
		
		NodeClass<E> removed = current.getNext();
		current.setNext(current.getNext().getNext());
		size--;
		return removed.getElement();
	}

	public E get(int pos) {
		int posAtual = 0;
		NodeClass<E> current = head;
		
		while (posAtual < pos) {
			current = current.getNext();
			posAtual++;
		}
		
		return current.getElement();
	}

	public int search(E element) {
		int pos = 0;
		NodeClass<E> current = head;
		while (current != element) {
			current = current.getNext();
			pos++;
		}
		return pos;
	}

	public void add(E element) {
		NodeClass<E> newNode = new NodeClass<E>(element);
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
		NodeClass<E> current;
		NodeClass<E> aux;
		E aux2;
		
		if (size >= 2) {
			 current = head;
			 aux = current.getNext();
			 
			 aux2 = current.getElement();
			 current.setElement(aux.getElement());
			 aux.setElement(aux2);
			 
			 for (int i = 1; i < size/2; i++) {
				 current = current.getNext().getNext();
				 aux = aux.getNext().getNext();
				 
				 aux2 = current.getElement();
				 current.setElement(aux.getElement());
				 aux.setElement(aux2);
			 }
		}
	}
	
	public void inverteLista() {
		if (size < 2)
			return;
		
		NodeClass<E> first = head;
		NodeClass<E> last = tail;
		E aux = first.getElement();
		
		first.setElement(last.getElement());
		last.setElement(aux);
		
		for (int i = 1; i < size/2; i++) {
			first = first.getNext();
			last = first;
			aux = first.getElement();
			
			for (int j = i + 1; j < size - i; j++) {
				last = last.getNext();
			}
			
			first.setElement(last.getElement());
			last.setElement(aux);
		}
	}
	
	public void removeNodes(int pos, int quantosNodes) {
		if (isEmpty())
			return;
		
		NodeClass<E> nodePos = head;
		int quantPos = 1;
		
		while (quantPos < pos) {
			nodePos = nodePos.getNext();
			quantPos++;
		}
		
		NodeClass<E> proxNode = nodePos.getNext();
		quantPos = 1;
		
		while (quantPos <= quantosNodes) {
			proxNode = proxNode.getNext();
			quantPos++;
		}
		
		nodePos.setNext(proxNode);
	}
	
	public void insereLista(SLinkedList<E> outraLista, int pos) {
		NodeClass<E> current = head;
		int posAtual = 0;
		
		while (posAtual < pos - 1) {
			current = current.getNext();
			posAtual++;
		}
		
		NodeClass<E> nextCurrent = current.getNext();
		current.setNext(outraLista.head);
		
		outraLista.tail.setNext(nextCurrent);
	}
	
	public void shuffle() {
		if (isEmpty() || size() == 1)
			return;
		
		for (int i = 0; i < size*2; i++) {
			int pos = (int)(Math.random() * size());
			E elemento = get(pos);
			
			remove(pos);
			add(elemento);
		}
	}
	
	
	public void sort(Comparator<E> c) {
		NodeClass<E> current = head;
		boolean troca = true;
		
		while (troca) {
			troca = false;
			
			for (int i = 0; i < size() - 1; i++) {
				if (c.compare(current.getElement(), current.getNext().getElement()) > 0) {
					E aux = current.getElement();
					current.setElement(current.getNext().getElement());
					current.getNext().setElement(aux);
					troca = true;
				}
			}
		}
	}
	
	public void duplica(int indice) {
		tail.setNext(new NodeClass<E>(get(indice)));
		tail = tail.getNext();
		size++;
	}
	
	public void removeDuplicados() {
		ArrayList<E> lista = new ArrayList<E>();
		NodeClass<E> last = head;
		NodeClass<E> current = head.getNext();
		int tamanho = size();
		lista.add(last.getElement());
		
		for (int i = 1; i < tamanho; i++) {
			if (lista.contains(current.getElement())) {
				last.setNext(current.getNext());
				size--;
			} else {
				lista.add(current.getElement());
				last = current;
			}

			current = current.getNext();
		}
	}
	
	public String toString() {
		String s = "";
		
		if (isEmpty()) {
			return "Lista vazia";
		}
		
		s += "Lista: \n";
		NodeClass<E> node = head;
		
		while (node != null) {
			s += node.getElement().toString() + "\n";
			node = node.getNext();
		}
		
		s += "\n";
		return s;
	}
}
