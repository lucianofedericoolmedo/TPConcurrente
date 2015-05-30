package tp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

	public static void main(String[] args) {
	
		List<Integer> elementos = new ArrayList<Integer>() ;//
				 //Stream.of(1,110,55,34,44,22,66,77,12,4).collect(Collectors.toList());
		int threadsDisponibles = 9;
		ListaConcurrente listaConcurrente = new ListaConcurrente(elementos,threadsDisponibles);
		
		Productor thread1 = new Productor(listaConcurrente);
		Productor thread2 = new Productor(listaConcurrente);	
		
		Consumidor thread3 = new Consumidor(listaConcurrente);
		Consumidor thread4 = new Consumidor(listaConcurrente);
		
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		
		System.out.println("Los elementos antes eran: " + listaConcurrente);
			
		listaConcurrente.quickSort();
		
		
		// print results
		System.out.println("Sorted numbers: " + listaConcurrente);
		System.out.println("Elements sorted: " + listaConcurrente.size());
	}

}