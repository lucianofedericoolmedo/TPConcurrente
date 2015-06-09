package tp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

	

	public static void main(String[] args) {
		
	    //Setup
		ListaConcurrente listaConMuchosElementos = new ListaConcurrente(new ArrayList<Integer>(), 2);
		System.out.println("** Test 16- Testo el ordenamiento de la quinta Lista**");
        for ( Integer i = 5 ; i > 0; i--){
        	listaConMuchosElementos.add(i);
        }
        System.out.println("sin ordenar numbers: " + listaConMuchosElementos);
        listaConMuchosElementos.quickSort();
     
        System.out.println("Sorted numbers: " + listaConMuchosElementos);
        System.out.println("Elements sorted: " + listaConMuchosElementos.size());
 
        
    }
}