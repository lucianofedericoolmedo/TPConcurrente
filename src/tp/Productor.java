package tp;



public class Productor extends Thread{
	
	ListaConcurrente buffer;
	
	public Productor(ListaConcurrente buffer) {
		this.buffer = buffer;
	}
	
	public void start(){
		this.run();
	}
	
	public void agregarElementos(){
		for (int n = 1; n <= 4; n++)
		{
			this.buffer.add((int) (Math.random() * 10));
		}
	}
	
	public void setearElementos(){
		for (int n = 0; n < buffer.size(); n++)
		{
			this.buffer.set(n,this.buffer.get(n) + n);
			//System.out.println("El thread " + this.getName() + " obtuvo: " + buffer.get(n));
		}
	}
	
	@Override
	public void run() {
		agregarElementos();
		//setearElementos();
		
	}
}
