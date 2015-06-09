package tp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ListaConcurrente {
	
	private List<Integer> representacion;
	int maxThreads;
		
	public ListaConcurrente (List<Integer> values, int cantThreads){
		this.representacion = values;		
		this.maxThreads = cantThreads;
	}

	public synchronized List<Integer> getRepresentacion() {
		return representacion;
	}
	
	public synchronized void setRepresentacion(List<Integer> lista) {	
		this.representacion= lista;
	}
	
	public synchronized int size(){
		return this.representacion.size();
	}
	
	public   Integer get (int posicion){
        return this.representacion.get(posicion);
		
	}
		
	public    void set (int posicion, Integer elemento){
		this.representacion.set(posicion, elemento);
		
	}

	public synchronized void add(Integer elemento) {
			this.representacion.add(elemento);
		
	}
	
	public synchronized boolean isEmpty(){
		return this.representacion.isEmpty();
	}

	public synchronized boolean contains(Integer elemento){
		return this.representacion.contains(elemento);
	}

	@Override
	public  String toString(){
		return this.representacion.toString();
	}
	
	public synchronized void quickSort() {
		Buffer buffer = new Buffer();
		Contador contador = new Contador(this.size()-1); 
		for(int i=0; i< this.maxThreads; i++){
			new Worker(this,buffer,contador).start();
		}
		Range init_range = new Range(0, this.size()-1);
		buffer.push(init_range);
		contador.waitZero();
		Range invalid_range = new Range(-1,0);
		for (int i = 0 ; i < maxThreads ; i++ ){
			buffer.push(invalid_range);
		}
	}
	
	public  int qsort_partition(int start, int end) {
    	int menoresAlPivot = 0;
        Integer pivot = this.get(start); 
        int n= start+1;

    	for (int i=n; i <= end; i++) {
    		
    		if (this.get(i) < pivot ) {
    	        swap(this, i, start + menoresAlPivot);
    	        menoresAlPivot++;
    		}
    	}
		return menoresAlPivot;
    }
    
    private  void swap(ListaConcurrente l,int index_a, int index_b) {
    	
		Integer a = l.get(index_a);
		Integer b = l.get(index_b);
		l.set(index_b, a);
		l.set(index_a, b);
	}
	
	public static void main(String[] args) {
		
	    //Setup
		ListaConcurrente listaConMuchosElementos = new ListaConcurrente(new ArrayList<Integer>(),10);
		System.out.println("** Test 16- Testo el ordenamiento de la quinta Lista**");
		 for ( Integer i = 10 ; i > 0; i--){
	        	listaConMuchosElementos.add(i);
	        }
		
        System.out.println("sin ordenar numbers: " + listaConMuchosElementos);
        listaConMuchosElementos.quickSort();
     
        System.out.println("Sorted numbers: " + listaConMuchosElementos);
        System.out.println("Elements sorted: " + listaConMuchosElementos.size());
 
        
    }
	

}