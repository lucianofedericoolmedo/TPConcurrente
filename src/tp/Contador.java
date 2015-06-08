package tp;

public class Contador {
	private int contador;
	public Contador(int size){
		this.contador = size;
	}
	
	public synchronized void dec(){
		if(contador == 0){
			notifyAll();
		}
		contador--;
	}
	public int getContador(){
		return this.contador;
	}
	public synchronized void waitZero(){
		while(contador != 0){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
