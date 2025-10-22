public interface List<E> {
	public boolean isEmpty();
	public void insert(E element, int pos);
	public E remove(int pos);
	public E get(int pos);
	public int search(E element);
	public void add(E element);	//insere no final
	public void clear(); //desaloca todos nodes da lista
}