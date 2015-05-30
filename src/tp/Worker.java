package tp;

import java.util.ArrayList;
import java.util.List;


public class Worker extends Thread{
	private static int THREADS_AVAILABLE = 1;
	private ListaConcurrente listaInicial;
	
    
    public Worker(ListaConcurrente listaInicial){
    	this.listaInicial = listaInicial;
    }
	
    public ListaConcurrente listaInicial(){
    	return listaInicial;
    }
	
		
	public ListaConcurrente sort() {
		List<Integer> less = new ArrayList<Integer>();
		List<Integer> more = new ArrayList<Integer>();
		
		int size = listaInicial.size();
		
		
		if (size<=1) return listaInicial;
		
		// Elijo al pivot y lo borro
		int pivot =  listaInicial.get(0);
		listaInicial.remove(Integer.valueOf(pivot));
		
		more = listaInicial.mayoresQue(pivot);
		less = listaInicial.menoresQue(pivot);
		
		
		// Let recursion begin ...
		if (THREADS_AVAILABLE > 1) {
	        THREADS_AVAILABLE--;
			return parallelSort(less,pivot,more);
		}
		else
			return concatenar(new Worker(new ListaConcurrente (less)).sort(),pivot,
					new Worker(new ListaConcurrente (more)).sort());
	}
	
	
	// Concatena la listas mergeadas de numeros menores y mayores
	public ListaConcurrente concatenar(ListaConcurrente less, int pivot, ListaConcurrente more) {
		
		// Creo la lista que va a mantener a los elementos ordenados
		List<Integer> sorted = new ArrayList<Integer>();
		
		sorted.addAll(less.getRepresentacion());
		sorted.add(pivot);
		sorted.addAll(more.getRepresentacion());
		
		// retorno la lista ordenada
		
		return new ListaConcurrente(sorted);
	}
	
    
    @Override
    public void run() {
        sort();
    }

  	private synchronized void startNewThread(ListaConcurrente listToSort) {
        new Worker(listToSort).start();
    }
    
	
	// Ordeno los elementos en paralelo y luego concateno las listas resultantes
	public ListaConcurrente parallelSort(final List<Integer> menoresQue, int pivot, final List<Integer> mayoresQue) {
		final ListaConcurrente left = new ListaConcurrente(menoresQue), right = new ListaConcurrente(mayoresQue);
		
		startNewThread(left);
		startNewThread(right);
		        
        THREADS_AVAILABLE++;
		return concatenar(left,pivot,right);
	}
	
	
}