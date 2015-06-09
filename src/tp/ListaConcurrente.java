package tp;

import java.util.List;

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
	
	public Integer get (int posicion){
        return this.representacion.get(posicion);		
	}
		
	public void set (int posicion, Integer elemento){
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
	public String toString(){
		return this.representacion.toString();
	}
	
	public synchronized void quickSort() {
		Buffer buffer = new Buffer();
		Contador contador = new Contador(this.size()-1); 
		for(int i=0; i< this.maxThreads; i++){
			new Worker(this,buffer,contador).start();
		}
		Range rangoInicial = new Range(0, this.size()-1);
		buffer.push(rangoInicial);
		contador.waitZero();
		Range rangoInvalido = new Range(-1,0);
		for (int i = 0 ; i < maxThreads ; i++ ){
			buffer.push(rangoInvalido);
		}
	}
	
	public int qsort_partition(int inicio, int fin) {
    	int menoresAlPivot = 0;
        Integer pivot = this.get(inicio); 
        int siguienteAInicio = inicio+1;
    	for (int i=siguienteAInicio; i <= fin; i++) {    		
    		if (this.get(i) < pivot ) {
    	        swap(i, inicio + menoresAlPivot);
    	        menoresAlPivot++;
    		}
    	}
    	System.out.println(menoresAlPivot);
		return menoresAlPivot;
    }
    
	private void swap(int indiceA, int indiceB) {    	
		Integer elementoEnA = this.get(indiceA);
		Integer elementoEnB = this.get(indiceB);
		this.set(indiceB, elementoEnA);
		this.set(indiceA, elementoEnB);
	}

}