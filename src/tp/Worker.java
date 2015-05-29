package tp;
import java.util.List;
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

	public synchronized void setLista(ListaConcurrente lista) {
		this.lista = lista;
	}
	

	

	public synchronized  ListaConcurrente sort(ListaConcurrente unaLista) {

        System.out.println("1-Soy la lista: " + unaLista.getRepresentacion() +" del " + this.getName());
        int pivot = this.getPivot(unaLista);
		System.out.println("2-Soy el pivot: " + pivot +" del " + this.getName());


        ListaConcurrente left = new ListaConcurrente(this.menoresQue(pivot, unaLista.getRepresentacion()));

        System.out.println("3-Soy el lista izq: " + left.getRepresentacion() +" del " + this.getName() );

        ListaConcurrente right = new ListaConcurrente(this.mayoresQue(pivot, unaLista.getRepresentacion()));

        System.out.println("4-Soy el lista der: " + right.getRepresentacion() +" del " + this.getName());


        if(prioridad -1 == prioridadMaxima){
            System.out.println("8-Soy el ultimo elem: " + pivot  +" del " + this.getName());
            System.out.println("9-tengo prioridad igual: " + prioridad + "==" + prioridadMaxima);
            unaLista.setOrdenado();
            unaLista.concat(left,pivot,right);
            return unaLista;
        }

        if(cantMaximaDeThreads!=0) {
            if(!left.isEmpty()) {

                System.out.println("5-Soy la listaA izq: " + left.getRepresentacion() +" del " + this.getName());
                Worker w1 = new Worker(left, cantMaximaDeThreads - 1, prioridad + 1, prioridadMaxima);
                w1.start();
                unaLista.procese();
                }

            if(!right.isEmpty()) {

                System.out.println("6-Soy la listaD der: " + right.getRepresentacion() +" del " + this.getName());
                Worker w2 = new Worker(right, cantMaximaDeThreads - 1, prioridad + 1, prioridadMaxima);
                w2.start();
                unaLista.procese();
                }


            }
        System.out.println("Soy la listaA izq antes de concatenar : " + left.getRepresentacion() +" del " + this.getName());
        System.out.println("5-Soy la listaA der antes de concatenar: " + right.getRepresentacion() +" del " + this.getName());

        unaLista.concat(left, pivot, right);

        return unaLista;
    }

    @Override
    public void run() {

            System.out.println("0-Soy  la lista antes de ejecutar: " + lista.getRepresentacion() +" del " + this.getName() );
            this.lista = sort(lista);

        System.out.println("7-CORTE: "+this.lista.getRepresentacion()+" de " + this.getName());

    }

    private synchronized Integer getPivot(ListaConcurrente listaConcurrente ) {
        return listaConcurrente.get(0);
    }



	private synchronized List<Integer> menoresQue(Integer pivot,List<Integer> lista){
		return lista.stream().filter(s -> s < pivot).collect(Collectors.toList());
	}

	private synchronized List<Integer> mayoresQue(Integer pivot,List<Integer> lista){

		return lista.stream().filter(s -> s > pivot).collect(Collectors.toList());
		}

	private synchronized boolean noTengoNadaMasQueOrdenar(ListaConcurrente listaConcurrente){
		return listaConcurrente.size() <= 1;
	}

    private synchronized boolean noTengoMasThreads(){
		return this.cantMaximaDeThreads == 0;
	}
}
