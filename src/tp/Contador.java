package tp;

public class Contador {
	private int contador;
	public Contador(int size){
		this.contador = size;
	}
	
	public synchronized void dec(){
		contador--;
		if(contador == 0){
			notifyAll();
		}
	}
	public synchronized int getContador(){
		return this.contador;
	}
	public synchronized void waitZero(){
		while(contador != 0){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
