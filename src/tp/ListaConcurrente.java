package tp;

import java.util.List;
import java.util.stream.Collectors;


public class ListaConcurrente {//extends Thread{
	
	public List<Integer> representacion;
	public  Integer cantMaximaDeThreads ;
	public Boolean ordenado= false;
	public int elementosOrdenados;

	public ListaConcurrente (List<Integer> values ){
		this.representacion = values;
		this.cantMaximaDeThreads =size()*2;
		this.elementosOrdenados = 0;

	}

	public synchronized void procese(){
		this.elementosOrdenados ++;
		if (! termineDeOrdenar()){
			waitWorker();
		}
		notifyAll();

	}

	public synchronized List<Integer> getRepresentacion() {
		return representacion;
	}

	public synchronized boolean getOrdenado(){
		return  ordenado;
	}

	public synchronized void setRepresentacion(List<Integer> listaInicial) 
	{	representacion= listaInicial;
		//listaInicial.stream().forEach(n -> this.add(n));
	}

	public synchronized void concat(ListaConcurrente less, int pivot, ListaConcurrente more) {
		less.add(pivot);
		less.agregarLista(more);
		this.representacion= less.getRepresentacion();
		this.setOrdenado();
		//notifyAll();
		System.out.println("CONCATENE");
		System.out.println("Lista Ordenada "+ this.getRepresentacion());
	}

	public void waitWorker(){
		try {

				wait();

		} catch (InterruptedException e) {}
	}

	public synchronized boolean termineDeOrdenar(){
		return elementosOrdenados == this.size();
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


	public synchronized List<Integer> menoresQue(Integer pivot,List<Integer> lista){
		return representacion.stream().filter(s -> s < pivot).collect(Collectors.toList());
	}

	public synchronized List<Integer> mayoresQue(Integer pivot,List<Integer> lista){
		return representacion.stream().filter(s -> s > pivot).collect(Collectors.toList());
	}

	public synchronized boolean noTengoNadaMasQueOrdenar(){
		return this.size() == 1;
	}
	
	private synchronized void waitLista(Integer posicion){
		while (this.size() < posicion || isEmpty()){
			try {
				wait();
			} catch (InterruptedException e) {}
		}
	}
	public synchronized void agregarLista(ListaConcurrente list){
		this.representacion.addAll(list.getRepresentacion());
	}

	public synchronized void add(Integer elemento) {
		if (! this.contains(elemento)){
			this.representacion.add(elemento);
			this.cantMaximaDeThreads =+2;
			notifyAll();
		}		
	}
	public synchronized void setOrdenado(){
		this.ordenado= true;
		notifyAll();
	}
	public synchronized boolean isEmpty(){
		return this.representacion.isEmpty();
	}
	

	public synchronized void quickSort() throws  InterruptedException{
		int prioridadMaxima= size()/2;
		Worker w = new Worker(this,cantMaximaDeThreads,0,prioridadMaxima);
		w.sort(this);
		this.setRepresentacion(w.getLista().getRepresentacion());
	}
	
	public synchronized boolean contains(Integer elemento){
		return this.representacion.contains(elemento);
	}



}
