package tp;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ConcuList extends Thread {

	private List<Integer> values;
	private int cantMaximaDeThreads;
	
	public ConcuList(Integer cantMaximaDeThreads, List<Integer> values){
		this.values = values;
		this.setCantMaximaDeThreads(cantMaximaDeThreads);
	}
	
	public int getCantMaximaDeThreads() {
		return cantMaximaDeThreads;
	}

	public void setCantMaximaDeThreads(int cantMaximaDeThreads) {
		this.cantMaximaDeThreads = cantMaximaDeThreads;
	}
	
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
	
	private synchronized int obtenerPosicionDePivot(){
		return this.values.size() / 2;
	}
	
	private synchronized int obtenerPivot(Integer indice){
		return this.values.get(indice);
	}
	
	public synchronized List<Integer> quicksort(){
		
		/*
		 * si la cant de threads es igual a la maxima, tengo que esperar
		 * 
		 * 
		 */
		
		if (this.values.size() <= 1){
			return this.values;
		}
		int pivot_index = obtenerPosicionDePivot();
		Integer pivot =  obtenerPivot(pivot_index);
		List<Integer> left = this.menoresQue(pivot);
		List<Integer> right = this.mayoresQue(pivot);
		/*
		 * 
		 * quicksort(left)
		 * quicksort(right)
		 */
		
		/*
		 * tendria que tener un contador de la cant de Threads
		 * 
		 *
		 */
		new ConcuList(getCantMaximaDeThreads() / 2, left).start();
		new ConcuList(getCantMaximaDeThreads() / 2, right).start();
		
		/*
		 * hago un signal
		 */
		left.add(pivot);
		return Stream.concat(left.stream(),right.stream()).collect(Collectors.toList());
	}
	

	public synchronized void imprimirLista(){
		System.out.print("tp.ConcuList contiene a: ");
		this.values.stream().forEach(n ->System.out.print(n));
	}

	@Override
	public void run() {
		quicksort();
		
	}
}

