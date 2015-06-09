package tp;

import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {

		ListaConcurrente listaConMuchosElementos = new ListaConcurrente(new ArrayList<Integer>(),10);
		System.out.println("** Test - Testo el ordenamiento de la lista con muchos elementos**");
		 for ( Integer i = 10000 ; i > 0; i--){
	        	listaConMuchosElementos.add( i );
	        }
		
        System.out.println("Lista con elementos sin ordenar: " + listaConMuchosElementos);
        listaConMuchosElementos.quickSort();
     
        System.out.println("Lista con elementos ordenados: " + listaConMuchosElementos);
        System.out.println("Elementos ordenados: " + listaConMuchosElementos.size());
        
    }
	
}