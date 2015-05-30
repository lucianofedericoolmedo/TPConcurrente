package tp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ListaConcurrente {
	
	public List<Integer> representacion;
	private int threadsDisponibles ;
	
	public ListaConcurrente (List<Integer> values, int threads ){
		this.representacion = values;
		this.setThreadsDisponibles(threads);
	}
	
	public int getThreadsDisponibles() {
		return threadsDisponibles;
	}

	public void setThreadsDisponibles(int threadsDisponibles) {
		this.threadsDisponibles = threadsDisponibles;
	}
	
	public synchronized void sort(Worker w) {
		List<Integer> less = new ArrayList<Integer>();
		List<Integer> more = new ArrayList<Integer>();
				
		if (this.noTengoNadaMasQueOrdenar()) {
			notifyAll();
			return ;
		}			
		
		// Elijo al pivot y lo borro
		int pivot =  this.get(0);
		this.remove(Integer.valueOf(pivot));
		
		more = mayoresQue(pivot);
		less = menoresQue(pivot);
		
		
		// Let recursion begin ...
		if (getThreadsDisponibles() > 1) {
			setThreadsDisponibles(getThreadsDisponibles() -1);
			w.parallelSort(less,pivot,more);
			
			
		} else{
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			w.concatenar(less,pivot,more);}
	}
	
	public synchronized List<Integer> menoresQue(Integer pivot){
        return this.representacion.stream().filter(s -> s < pivot).collect(Collectors.toList());	
	}
	
	public synchronized List<Integer> mayoresQue(Integer pivot){
        return this.representacion.stream().filter(s -> s > pivot).collect(Collectors.toList());	
	}	
	
	public synchronized List<Integer> getRepresentacion() {
		return representacion;
	}
	
	public synchronized void setRepresentacion(List<Integer> listaInicial) 
	{	representacion= listaInicial;
	}
	
	public synchronized int size(){
		return this.representacion.size();
	}
	
	public synchronized Integer get (Integer posicion){
		waitLista(posicion);
		return this.representacion.get(posicion);
	}
		
	public synchronized void set (Integer posicion, Integer elemento){
		waitLista(posicion);
		this.representacion.set(posicion, elemento);
	}

	public synchronized boolean noTengoNadaMasQueOrdenar(){
		return this.size() <= 1;
	}
	
	private synchronized void waitLista(Integer posicion){
		while (this.size() < posicion || this.isEmpty()){
			try {
				wait();
			} catch (InterruptedException e) {}
		}
	}

	public synchronized void add(Integer elemento) {
		if (! this.contains(elemento)){
			this.representacion.add(elemento);
			notifyAll();
		}		
	}
	
	public synchronized boolean isEmpty(){
		return this.representacion.isEmpty();
	}
	
	public synchronized void quickSort() {
		Worker w = new Worker(this);
		w.start();
	}
	
	public synchronized boolean contains(Integer elemento){
		return this.representacion.contains(elemento);
	}

	@Override
	public synchronized String toString(){
		return this.representacion.toString();
	}
	
	public synchronized void imprimirLista(){
		System.out.print("Esta lista concurrente contiene a: ");
		System.out.print(this.toString());
	}
	
	public synchronized void remove(Integer i) {
		this.representacion.remove(i);
		
	}
}