package tp;

import java.util.ArrayList;

public class Buffer {
	
	private ArrayList<Range> values ;
	
	public Buffer() {
		this.values = new ArrayList<Range>();
	}
	
	public synchronized boolean isEmpty(){
		return this.values.isEmpty();
	}
	
	public synchronized int size(){
		return this.values.size();
	}

	public synchronized ArrayList<Range> getValues(){
		return this.values;
	}
	
	public synchronized void setValues(ArrayList<Range> values){
		this.values= values;
	}

	
	public synchronized void push(Range v) {
		notifyAll();
		getValues().add(v);
	}
	
	public synchronized Range pop() {
		while (isEmpty()){
			try { wait(); } catch (InterruptedException e) {}
		}
		Range result = this.getValues().get(0);
		this.getValues().remove(0);
		notifyAll();
		return result;
	}
	
	public static void main(String[] args) {
		Buffer b= new Buffer();
        Range init_range = new Range(0, 10);
        b.push(init_range);
        System.out.println("Rango: " + b.size());
        b.push(init_range);
        System.out.println("Rango: " + b.size());
	}
}
	
