import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ConcuList{

	private List<Integer> values = new ArrayList<Integer>();
	
	public synchronized int size(){
		return this.values.size();
	}
	
	public synchronized boolean isEmpty(){
		return this.values.isEmpty();
	}
	
	public synchronized boolean contains(Integer elemento){
		return this.values.contains(elemento);
	}
	
	public synchronized void add(Integer elemento) {
		if (! this.contains(elemento)){
			this.values.add(elemento);
			notifyAll();
		}		
	}
	
	public synchronized Object get (int posicion){
		waitLista(posicion);
		return this.values.get(posicion);
	}
		
	
	public synchronized void set (Integer posicion, Integer elemento){
		waitLista(posicion);
		this.values.set(posicion, elemento);
	}	
	
	private synchronized void waitLista(Integer posicion){
		while (this.values.size() < posicion || isEmpty()){
			try {
				wait();
			} catch (InterruptedException e) {}
		}
	}
	//
	
	private synchronized List<Integer> menoresQue(Integer pivot){
        return this.values.stream().filter(s -> s < pivot).collect(Collectors.toList());	
	}
	
	private synchronized List<Integer> mayoresQue(Integer pivot){
        return this.values.stream().filter(s -> s > pivot).collect(Collectors.toList());	
	}
	
	public synchronized List<Integer> quicksort(){
		if (this.values.size() <= 1){
			return this.values;
		}
		int pivot_index = this.values.size() / 2;
		Integer pivot =  this.values.get(pivot_index);
		List<Integer> left = this.menoresQue(pivot);
		List<Integer> right = this.mayoresQue(pivot);
		/*
		 * 
		 * quicksort(left)
		 * quicksort(right)
		 */
		left.add(pivot);
		return Stream.concat(left.stream(),right.stream()).collect(Collectors.toList());
	}
}
