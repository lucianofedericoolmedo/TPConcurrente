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
		for (int n = 0; n < buffer.size(); n++)
		{
			System.out.println("Soy el thread " + this.getName() + " y consumi: " + buffer.get(n));
		}
	}
	
	
	
	@Override
	public void run() {		
		obtenerElementos();		
	}
}
