package tp;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ListaConcurrente {
	
	public List<Integer> representacion;
	int maxThreads;
		
	public ListaConcurrente (List<Integer> values, int cantThreads){
		this.representacion = values;		
		this.maxThreads = cantThreads;
	}

	public synchronized void quickSort() throws InterruptedException {
		Buffer buffer = new Buffer();
		CountDownLatch contador = new CountDownLatch(maxThreads); 
		ExecutorService executorService = Executors.newFixedThreadPool(maxThreads);
		Range init_range = new Range(0, this.size()-1);
		buffer.push(init_range); // agrega el trabajo inicial de ordenar toda la lista
		Runnable task = new Worker(this,buffer,contador) {
			public void run() {
				qsort_worker();
			}
		};
		executorService.execute(task);
		
		contador.await();
		executorService.shutdown();
		Range invalid_range = new Range(1,0);
		for (int i = 0 ; i < maxThreads ; i++ ){
			buffer.push(invalid_range);
		}
	}
	
	public synchronized List<Integer> getRepresentacion() {
		return representacion;
	}
	
	public synchronized void setRepresentacion(List<Integer> lista) 
	{	representacion= lista;
	}
	
	public synchronized int size(){
		return this.representacion.size();
	}
	
	public synchronized Integer get (Integer posicion){
		return this.representacion.get(posicion);
	}
		
	public synchronized void set (Integer posicion, Integer elemento){
		if (! this.contains(elemento)) {
			this.representacion.set(posicion, elemento);
		}
	}

	public synchronized void add(Integer elemento) {
		if (! this.contains(elemento)){
			this.representacion.add(elemento);
		}		
	}
	
	public synchronized boolean isEmpty(){
		return this.representacion.isEmpty();
	}

	public synchronized boolean contains(Integer elemento){
		return this.representacion.contains(elemento);
	}

	@Override
	public synchronized String toString(){
		return this.representacion.toString();
	}
}