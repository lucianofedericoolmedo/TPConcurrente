package tp;

import java.util.ArrayList;

import tp.Buffer;
import tp.ListaConcurrente;
import tp.Range;

public class Worker extends Thread{
	
	private ListaConcurrente miListaConcurrente;
	public Buffer buffer;
	private Contador contador;
	
    public Worker(ListaConcurrente listaInicial, Buffer buffer, Contador c){
    	this.buffer = buffer;
    	this.contador = c;
    	this.miListaConcurrente = listaInicial;
    }
	
    public ListaConcurrente listaInicial(){
    	return miListaConcurrente;
    }
 
    
   @Override
   public void run() {
	   qsort_worker(this.miListaConcurrente, this.buffer,this.contador);
	}

   public void qsort_worker(ListaConcurrente l, Buffer buffer, Contador c) { 
    	while (true) {
    		
    		Range r = (Range) buffer.pop(); // consume trabajo (bloqueante)

            if (!r.isValid()) {
                System.out.println("Soy invalido");
                return;
    		}

            if (r.size()>1) {

            	int count = this.qsort_partition( l,r.start, r.end);
            	buffer.push(new Range(r.start, r.start + count - 1));// agrega trabajo
    			buffer.push(new Range(r.start+ count + 1 , r.end)); // agrega trabajo

    		} 

    		if (! r.isEmpty()) {
                c.dec();

    		}
    	}
    	
    }
   
	private int qsort_partition(ListaConcurrente l,int start, int end) {
    	int menoresAlPivot = 0;
        Integer pivot = l.get(start); 
        int n= start+1;

    	for (int i=n; i <= end; i++) {
    		
    		if (l.get(i) < pivot ) {
    	        
    	        swap(l, i, start + 1 + menoresAlPivot);
    	        menoresAlPivot++;
    		}
    	}
    	swap(l,start, start+menoresAlPivot);
    	return menoresAlPivot;
    }
    
    private  void swap(ListaConcurrente l,int index_a, int index_b) {
    	
		Integer a = l.get(index_a);
		Integer b = l.get(index_b);
		l.set(index_b, a);
		l.set(index_a, b);
	}
    
        

}