package tp;
import java.util.ArrayList;
import java.util.List;


public class Worker extends Thread{
	
	private static int THREADS_AVAILABLE = 1;
	private ListaConcurrente miListaConcurrente;
	
    
    public Worker(ListaConcurrente listaInicial){
    	this.miListaConcurrente = listaInicial;
    }
	
    public ListaConcurrente listaInicial(){
    	return miListaConcurrente;
    }
	
    
	public ListaConcurrente sort() {
		List<Integer> less = new ArrayList<Integer>();
		List<Integer> more = new ArrayList<Integer>();
				
		if (miListaConcurrente.noTengoNadaMasQueOrdenar()) return miListaConcurrente;
		
		// Elijo al pivot y lo borro
		int pivot =  miListaConcurrente.get(0);
		miListaConcurrente.remove(Integer.valueOf(pivot));
		
		more = miListaConcurrente.mayoresQue(pivot);
		less = miListaConcurrente.menoresQue(pivot);
		
		
		// Let recursion begin ...
		if (THREADS_AVAILABLE > 1) {
	        THREADS_AVAILABLE--;
			return parallelSort(less,pivot,more);
		}
		else
			return concatenar(new Worker(new ListaConcurrente (less)).sort(),pivot,
					new Worker(new ListaConcurrente (more)).sort());
	}
	
	public void sort2() {
		List<Integer> less = new ArrayList<Integer>();
		List<Integer> more = new ArrayList<Integer>();
				
		if (miListaConcurrente.noTengoNadaMasQueOrdenar()) return ;
		
		// Elijo al pivot y lo borro
		int pivot =  miListaConcurrente.get(0);
		miListaConcurrente.remove(Integer.valueOf(pivot));
		
		more = miListaConcurrente.mayoresQue(pivot);
		less = miListaConcurrente.menoresQue(pivot);
		
		
		// Let recursion begin ...
		if (THREADS_AVAILABLE > 1) {
	        THREADS_AVAILABLE--;
			parallelSort2(less,pivot,more);
		}
		else
			concatenar2(new Worker(new ListaConcurrente (less)).sort(),pivot,
					new Worker(new ListaConcurrente (more)).sort());
	}
	

	
	
	
	private void concatenar2(ListaConcurrente less, int pivot,
			ListaConcurrente more) {
		
		// Creo la lista que va a mantener a los elementos ordenados
		List<Integer> sorted = new ArrayList<Integer>();
		
		sorted.addAll(less.getRepresentacion());
		sorted.add(pivot);
		sorted.addAll(more.getRepresentacion());
		
		// retorno la lista ordenada
		
		this.listaInicial().setRepresentacion(sorted);
		
	}

	private void parallelSort2(List<Integer> less, int pivot, List<Integer> more) {
		final ListaConcurrente left = new ListaConcurrente(less), right = new ListaConcurrente(more);
		
		startNewThread(left);
		startNewThread(right);
		        
        THREADS_AVAILABLE++;
		concatenar(left,pivot,right);
		
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
        sort2();
    }
    
    @Override
    public void start() {
        run();
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