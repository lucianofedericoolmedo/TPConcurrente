package tp;


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

            if (!r.isValid()) 
            	return;
            
            if (r.size() >=1) {
            	int count =l.qsort_partition(r.start,r.end );
            	Range right= new Range(r.start, r.start+count-1);
            	Range left= new Range(r.start+count+ 1 , r.end);
            	if(right.isValid())
            		buffer.push(right);// agrega trabajo
            	if(left.isValid())
            		buffer.push(left); // agrega trabajo
            } 
            if (! r.isEmpty()) 
                c.dec();

    		}
    	}
    	
    }
   
	
    
        