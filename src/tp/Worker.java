package tp;
import java.util.ArrayList;
import java.util.List;


public class Worker extends Thread{
	
	//private static int THREADS_AVAILABLE = 10;
	private ListaConcurrente miListaConcurrente;
	
    
    public Worker(ListaConcurrente listaInicial){
    	this.miListaConcurrente = listaInicial;
    }
	
    public ListaConcurrente listaInicial(){
    	return miListaConcurrente;
    }
	
    
	public void sort() {
		List<Integer> less = new ArrayList<Integer>();
		List<Integer> more = new ArrayList<Integer>();
				
		if (miListaConcurrente.noTengoNadaMasQueOrdenar()) return ;
		
		// Elijo al pivot y lo borro
		int pivot =  miListaConcurrente.get(0);
		miListaConcurrente.remove(Integer.valueOf(pivot));
		
		more = miListaConcurrente.mayoresQue(pivot);
		less = miListaConcurrente.menoresQue(pivot);
		
		
		// Let recursion begin ...
		if (miListaConcurrente.getThreadsDisponibles() > 1) {
			miListaConcurrente.setThreadsDisponibles(miListaConcurrente.getThreadsDisponibles() -1);
			parallelSort(less,pivot,more);
			
		}
		else
			concatenar(less,pivot,more);
	}
	
	private void concatenar(List<Integer> less, int pivot,
			List<Integer> more) {
		
		// Creo la lista que va a mantener a los elementos ordenados
		List<Integer> sorted = new ArrayList<Integer>();
		
		sorted.addAll(less);
		sorted.add(pivot);
		sorted.addAll(more);
		
		// retorno la lista ordenada
		
		this.listaInicial().setRepresentacion(sorted);
		
	}

	private void parallelSort(List<Integer> less, int pivot, List<Integer> more) {
		final ListaConcurrente left = new ListaConcurrente(less,miListaConcurrente.getThreadsDisponibles()), 
				right = new ListaConcurrente(more,miListaConcurrente.getThreadsDisponibles());
		
		startNewThread(left);
		startNewThread(right);
		        
		miListaConcurrente.setThreadsDisponibles(miListaConcurrente.getThreadsDisponibles() + 1);
		concatenar(left.getRepresentacion(),pivot,right.getRepresentacion());
		
	}

    @Override
    public void run() {
        sort();
    }
    
    @Override
    public void start() {
        run();
    }
    

  	private synchronized void startNewThread(ListaConcurrente listToSort) {
        new Worker(listToSort).start();
    }
    
}