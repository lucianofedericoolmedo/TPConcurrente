package tp;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

	public static void main(String[] args) {
		List<Integer> unsorted = Stream.of(111,33,2).collect(Collectors.toList());
		List<Integer> vacia= new ArrayList<Integer>();
		ListaConcurrente concuList = new ListaConcurrente(vacia);
		for(Integer numero:unsorted){
			concuList.add(numero);
		}
		System.out.println("Numeros no ordenados: " + 3/2);
		System.out.println("Numeros no ordenados: " + concuList.getRepresentacion());
		try {
			concuList.quickSort();
			TimeUnit.SECONDS.sleep(10);
		}catch (InterruptedException e){}

		System.out.println("Numeros ordenados: " + concuList.getRepresentacion());
		System.out.println("Elementos ordenados: " + concuList.size());
	}
	
}