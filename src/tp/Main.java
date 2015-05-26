package tp;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		
		Integer cantDeThreads = 2;
		List<Integer> values = new ArrayList<Integer>();
		ConcuList buffer = new ConcuList(cantDeThreads,values);
		Agrega agregaElementos = new Agrega(buffer);
		agregaElementos.start();
		Agrega agregaElementos2 = new Agrega(buffer);
		agregaElementos2.start();
		buffer.imprimirLista();
	}
	
}