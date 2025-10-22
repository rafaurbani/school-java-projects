public interface List<E> {
	public boolean isEmpty();
	public boolean isFull();
	public void insert(E element, int pos) throws IndexOutOfBoundsException, OverflowException;
	public E remove(int pos) throws UnderflowException;
	public E get(int pos);
	public int search(E element);
	public void add(E element) throws OverflowException;	//insere no final
	public void clear(); //desaloca todos nodes da lista
}

