package tp;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

	public static void main(String[] args) {
	
		List<Integer> elementos =  Stream.of(1,111,34,44,22,66,77,12,4).collect(Collectors.toList());
		ListaConcurrente unsorted = new ListaConcurrente(elementos);
		System.out.println("Los elementos antes eran: " + unsorted);
			
		unsorted.quickSort();
		
		
		// print results
		System.out.println("Sorted numbers: " + unsorted);
		System.out.println("Elements sorted: " + unsorted.size());
	}

}