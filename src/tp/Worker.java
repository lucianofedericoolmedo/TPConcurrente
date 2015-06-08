package tp;

import java.util.concurrent.CountDownLatch;

import prueba.Buffer;
import prueba.ListaConcurrente;
import prueba.Range;

public class Worker extends Thread{
	
	private ListaConcurrente miListaConcurrente;
	private Buffer buffer;
	private CountDownLatch contador;
	
    public Worker(ListaConcurrente listaInicial, Buffer buffer, CountDownLatch c){
    	this.buffer = buffer;
    	this.contador = c;
    	this.miListaConcurrente = listaInicial;
    }
	
    public ListaConcurrente listaInicial(){
    	return miListaConcurrente;
    }
    
    void qsort_worker() { 
    	while (true) {
    		Range r = (Range) buffer.pop(); // consume trabajo (bloqueante)
    		if (!r.isValid()) {
    			return;
    		}
    		if (r.size()>1) {
    			int count = qsort_partition( r.start, r.end);
    			Range new_left_range = new Range(r.start, r.start+count-1);
    			Range new_right_range = new Range(r.start+count+1, r.end);
    			buffer.push(new_left_range);  // agrega trabajo
    			buffer.push(new_right_range); // agrega trabajo
    			contador.notifyAll();
    		} 
    		if (! buffer.isEmpty()) {
    			contador.countDown();
    		}
    	}
    }
        
    int qsort_partition( int start, int end) {
    	int j = 0;
    	int pivot = miListaConcurrente.get(start); 
    	for (int i=start+1; i<=end; i++) {
    		if (miListaConcurrente.get(i)<pivot) {
    			swap(i, start+1 + j);
    			j++;
    		}
    	}
    	swap(start, start+j);
    	return j;
    }
    
//    private int partition(int left, int right, int pivot){
//        int leftCursor = left - 1;
//        int rightCursor = right;
//        while(leftCursor < rightCursor){
//            while(miListaConcurrente.get(++leftCursor) < pivot);
//            while(rightCursor > 0 && miListaConcurrente.get(--rightCursor) > pivot);
//
//            if(leftCursor < rightCursor)
//            	swap(leftCursor, rightCursor);
//        }
//        swap(leftCursor, right);
//        return leftCursor;
//    }
    
    private void swap(int index_a, int index_b) {
		int a = miListaConcurrente.get(index_a);
		int b = miListaConcurrente.get(index_b);
		miListaConcurrente.set(index_b, a);
		miListaConcurrente.set(index_a, b);
	}
    

//	public void concatenar(List<Integer> less, int pivot,
//			List<Integer> more) {
//		
//		// Creo la lista que va a mantener a los elementos ordenados
//		List<Integer> sorted = new ArrayList<Integer>();
//		
//		sorted.addAll(less);
//		sorted.add(pivot);
//		sorted.addAll(more);		
//		// retorno la lista ordenada		
//		this.listaInicial().setRepresentacion(sorted);		
//	}
//	
//    @Override
//    public void run() {
//    	qsort_worker();
//        //miListaConcurrente.sort(this);
//    }
//    
//    @Override
//    public void start() {
//        run();
//    }
//    
//
////  	private void startNewThread(ListaConcurrente listToSort) {
////        new Worker(listToSort).start();
////    }
//    
}