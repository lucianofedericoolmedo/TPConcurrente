package tp;

public class Consumidor extends Thread{
	
	ListaConcurrente buffer;
	
	public Consumidor(ListaConcurrente buffer) {
		this.buffer = buffer;
	}
	
	public void start(){
		this.run();
	}
	
	
	public void obtenerElementos(){
		for (int n = 0; n < 4; n++)
		{
			System.out.println("El thread " + this.getName() + " obtuvo: " + buffer.get(n));
		}
	}
	
	
	
	@Override
	public void run() {		
		obtenerElementos();		
	}
}
