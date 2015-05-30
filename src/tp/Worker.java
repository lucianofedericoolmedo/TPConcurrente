package tp;
import java.util.ArrayList;
import java.util.List;


public class Worker extends Thread{
	
	private ListaConcurrente miListaConcurrente;
	
    
    public Worker(ListaConcurrente listaInicial){
    	this.miListaConcurrente = listaInicial;
    }
	
    public ListaConcurrente listaInicial(){
    	return miListaConcurrente;
    }

	
	public void concatenar(List<Integer> less, int pivot,
			List<Integer> more) {
		
		// Creo la lista que va a mantener a los elementos ordenados
		List<Integer> sorted = new ArrayList<Integer>();
		
		sorted.addAll(less);
		sorted.add(pivot);
		sorted.addAll(more);
		
		// retorno la lista ordenada
		
		this.listaInicial().setRepresentacion(sorted);
		
	}

	public void parallelSort(List<Integer> less, int pivot, List<Integer> more) {
		final ListaConcurrente left = new ListaConcurrente(less,miListaConcurrente.getThreadsDisponibles()), 
				right = new ListaConcurrente(more,miListaConcurrente.getThreadsDisponibles());
		
		startNewThread(left);
		startNewThread(right);
		        
		miListaConcurrente.setThreadsDisponibles(miListaConcurrente.getThreadsDisponibles() + 1);
		concatenar(left.getRepresentacion(),pivot,right.getRepresentacion());
		
	}

    @Override
    public void run() {
        miListaConcurrente.sort(this);
    }
    
    @Override
    public void start() {
        run();
    }
    

  	private synchronized void startNewThread(ListaConcurrente listToSort) {
        new Worker(listToSort).start();
    }
    
}