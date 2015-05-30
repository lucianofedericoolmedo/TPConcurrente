package tp;

import java.util.List;
import java.util.stream.Collectors;


public class ListaConcurrente {//extends Thread{
	
	public List<Integer> representacion;
	public  Integer cantMaximaDeThreads ;
	public Boolean ordenado= false;
	
	public ListaConcurrente (List<Integer> values ){
		this.representacion = values;
		this.cantMaximaDeThreads =size()*2;
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
	public boolean getOrdenado(){
		return  ordenado;
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
		return this.size() == 1;
	}
	
	private synchronized void waitLista(Integer posicion){
		while (this.size() < posicion || isEmpty()){
			try {
				wait();
			} catch (InterruptedException e) {}
		}
	}
	public void concat(ListaConcurrente list){
		this.representacion.addAll(list.getRepresentacion());
	}
	public synchronized void add(Integer elemento) {
		if (! this.contains(elemento)){
			this.representacion.add(elemento);
			this.cantMaximaDeThreads =+2;
			notifyAll();
		}		
	}
	public void setOrdenado(){
		this.ordenado= true;
	}
	public synchronized boolean isEmpty(){
		return this.representacion.isEmpty();
	}
	

	public synchronized void quickSort() {
		Worker w = new Worker(this);
		this.representacion = w.sort().getRepresentacion();
	}
	
	public synchronized boolean contains(Integer elemento){
		return this.representacion.contains(elemento);
	}

	@Override
	public String toString(){
		return this.representacion.toString();
	}
	
	public synchronized void imprimirLista(){
		System.out.print("Esta lista concurrente contiene a: ");
		System.out.print(this.toString());
	}
	
	public void remove(Integer i) {
		this.representacion.remove(Integer.valueOf(i));
		
	}



}
