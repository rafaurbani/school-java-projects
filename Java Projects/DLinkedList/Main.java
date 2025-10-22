
public class Main {
	public static void main(String[] args) throws OverflowException, UnderflowException {
		DLinkedList<Character> list = new DLinkedList<Character>();
		
		list.add('f');
		list.add('e');
		list.add('d');
		list.add('c');
		list.add('b');
		list.add('a');
		
		System.out.println(list);
		
		list.insert('z', 3);
		
		
		System.out.println(list);
		
		//--------------------------------------------------------
		
		LinkedQueue<Integer> queue = new LinkedQueue<Integer>();
		
		queue.enqueue(9);
		queue.enqueue(18);
		queue.enqueue(4);
		queue.enqueue(100);
		queue.enqueue(28);
		
		System.out.println(queue);

		System.out.println(queue.dequeue());
		
		System.out.println(queue); 
		
		//--------------------------------------------------------

		LinkedStack<Integer> stack = new LinkedStack<Integer>();
		
		stack.push(9);
		stack.push(18);
		stack.push(4);
		stack.push(100);
		stack.push(28);
		
		System.out.println(stack);
		System.out.println(stack.pop());
		System.out.println(stack);
		System.out.println(stack.top()); 
		
		//--------------------------------------------------------

		LinkedDeque<Integer> deque = new LinkedDeque<Integer>();
		
		deque.enqueue(90);
		deque.enqueue(180);
		deque.enqueue(40);
		deque.enqueue(1000);
		deque.enqueue(280);
		deque.push(-9);
		deque.push(-18);
		deque.push(-4);
		deque.push(-100);
		deque.push(-28);
		deque.pop();
		
		System.out.println(deque);
		
	}
	
}
