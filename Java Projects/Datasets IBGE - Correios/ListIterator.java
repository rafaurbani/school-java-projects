
public interface ListIterator<E> {
	public E current();
	public E next(); 
	public E previous(); 
	public boolean hasNext(); 
	public boolean hasPrevious(); 
	public void reset();
	public void addAfter(E elem) throws IndexOutOfBoundsException, OverflowException; 
	public void addBefore(E elem) throws IndexOutOfBoundsException, OverflowException;
	public void remove() throws UnderflowException; 
}
