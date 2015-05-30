package tp;

import prueba.ListaConcurrente;

public class Consumidor extends Thread{
	
	ListaConcurrente buffer;
	
	public Consumidor(ListaConcurrente buffer) {
		this.buffer = buffer;
	}
	
	public void start(){
		this.run();
	}
	
	
	public void obtenerElementos(){
		for (int n = 0; n < buffer.size(); n++)
		{
			System.out.println("El thread " + this.getName() + " obtuvo: " + buffer.get(n));
		}
	}
	
	
	
	@Override
	public void run() {		
		obtenerElementos();		
	}
}
