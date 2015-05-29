package tp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


public class Worker extends Thread{
	
	private ListaConcurrente  lista;
	private int cantMaximaDeThreads;
	private  int prioridad;
	private  int prioridadMaxima;
    public Worker(ListaConcurrente  lista, int cantMaximaDeThreads, int prioridad, int prioridadMaxima){
		
		this.lista = lista;
		this.cantMaximaDeThreads=cantMaximaDeThreads;
		this.prioridad= prioridad;
		this.prioridadMaxima= prioridadMaxima;

	}
	
	public ListaConcurrente getLista() {
		return lista;
	}

	public void setLista(ListaConcurrente lista) {
		this.lista = lista;
	}
	
	@Override
	public void run() {
		try {
            sort();
        }catch ( InterruptedException e){}

		System.out.println("Soy un thread: " + this.getName());

	}
	
	public void waitWorker(){
		try {
			if (prioridad != prioridadMaxima)
				wait();

		} catch (InterruptedException e) {}
	}

	public synchronized  void sort() throws InterruptedException{

			int pivot = this.getPivot();
			System.out.println("Soy el pivot: " + pivot);
			ListaConcurrente left = new ListaConcurrente(this.menoresQue(pivot, lista.getRepresentacion()));
			ListaConcurrente right = new ListaConcurrente(this.mayoresQue(pivot, lista.getRepresentacion()));

        if (noTengoNadaMasQueOrdenar()) {
            System.out.println("tengo prioridad igual: " + prioridad + "==" + prioridadMaxima);
            this.lista.setOrdenado();

            return;
        }
            if(cantMaximaDeThreads!=0) {
                Worker w1 = new Worker(left, cantMaximaDeThreads-1, prioridad + 1, prioridadMaxima);
                w1.start();
                Worker w2 = new Worker(right, cantMaximaDeThreads-1, prioridad + 1, prioridadMaxima);
                w2.start();
                concat(left, pivot, right);

            }


	}

    private Integer getPivot( ) {
        return lista.get(0);
    }

    public synchronized void concat(ListaConcurrente less, int pivot, ListaConcurrente more) {
		less.add(pivot);
		less.concat(more);
		this.lista= less;
		this.lista.setOrdenado();

	}

	private synchronized List<Integer> menoresQue(Integer pivot,List<Integer> lista){
		return lista.stream().filter(s -> s < pivot).collect(Collectors.toList());
	}

	private synchronized List<Integer> mayoresQue(Integer pivot,List<Integer> lista){
		return lista.stream().filter(s -> s > pivot).collect(Collectors.toList());
		}

	private synchronized boolean noTengoNadaMasQueOrdenar(){
		return this.lista.size() <= 1;
	}

    private synchronized boolean noTengoMasThreads(){
		return this.cantMaximaDeThreads == 0;
	}
}
