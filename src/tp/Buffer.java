package prueba;

import java.util.Stack;
public class Buffer {

	private Stack<Range> values = new Stack<Range> ();
	
	public boolean isEmpty() { return values.isEmpty(); }
	
	public synchronized void push(Range new_left_range) {
		values.add(new_left_range);
		notifyAll();
	}
	
	public synchronized Range pop() {
		while (isEmpty())
			try { wait(); } catch (InterruptedException e) {}
		Range result = values.pop();
		notifyAll();
		return result;
	}
	
}