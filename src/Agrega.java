import java.util.ArrayList;
import java.util.List;


public class Agrega extends Thread{
	
	private ConcuList buffer;
	
	public Agrega(ConcuList buffer) {
		this.buffer = buffer;
	}
	
	@Override
	public void run() {
		
		for (int n = 1; n <= 4; n++)
		{
			this.buffer.add(n);
		}
		
		System.out.println("El thread " + this.getName() + " tiene: " + buffer.size() + " elementos ");
		
		for (int n = 0; n < 4; n++)
		{
			System.out.println("El thread " + this.getName() + " obtuvo: " + buffer.get(n));
			this.buffer.add(n);
		}

	}
}
