package tp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

	

	public static void main(String[] args) {
		
	    //Setup
		List<Integer> listaVacia = new ArrayList<Integer>() ;
		ListaConcurrente listaConcurrente = new ListaConcurrente(listaVacia,2);
        List<Integer> listaVacia2 = new ArrayList<Integer>() ;
        ListaConcurrente listaProductor= new ListaConcurrente(listaVacia2, 2);
        List<Integer> listaVacia3 = new ArrayList<Integer>() ;
        ListaConcurrente listaCompartida1= new ListaConcurrente(listaVacia3, 2);
        List<Integer> listaVacia4 = new ArrayList<Integer>() ;
        ListaConcurrente listaCompartida2= new ListaConcurrente(listaVacia4, 2);
        ListaConcurrente listaConMuchosElementos = new ListaConcurrente(new ArrayList<Integer>(), 2);

        // Testo el comportamiento de la ListaConcurrente
        testListaInicializada(listaConcurrente);
        testAgregoUnElementoALaLista(listaConcurrente);
        testAgregoUnElmentoYestaRepetido(listaConcurrente);
        testComprueboSiTengoUnElementoEnLaLista(listaConcurrente);
        testSeteoUnElemento(listaConcurrente);
        testSeteoUnElementoYEstaRepetido(listaConcurrente);

        //Testo el comportamiento de un Productor

        testInicializoUnProductorYAgrega(listaProductor);
        testAgregoOtroProductorYAgrega(listaProductor);

        // Testeo  el comportamiento de un consumidor
        testInicializarConsumidor(listaProductor);

        //Testo el comportamiento de un consumidor con un productor
        testUnConsumidorYUnProductor(listaCompartida1);
        testComportamientoDosConsumidoresYUnProductor(listaCompartida2);

        //Testo el Ordenamiento de las listas
        testDeOrdenamiento(listaConcurrente, listaProductor, listaCompartida1, listaCompartida2);
        
        //Testeo el Ordenamiento de las listas con muchos elementos
        testDeOrdenamientoParaListaConMuchosElementos(listaConMuchosElementos);
    }

	private static void testDeOrdenamientoParaListaConMuchosElementos(ListaConcurrente listaConMuchosElementos) {
        System.out.println("** Test 16- Testo el ordenamiento de la quinta Lista**");
        for (int i = 0 ; i < 1000; i++){
        	listaConMuchosElementos.add(i);
        }    
        
        testeoElOrdenamientoDeLaLista(listaConMuchosElementos);
    }
	
	
    private static void testDeOrdenamiento(ListaConcurrente listaConcurrente, ListaConcurrente listaProductor, ListaConcurrente listaCompartida1, ListaConcurrente listaCompartida2) {
        System.out.println("** Test 12- Testo el ordenamiento de la primer Listas**");
        testeoElOrdenamientoDeLaLista(listaConcurrente);
        System.out.println("** Test 13- Testo el ordenamiento de la segunda Lista**");
        testeoElOrdenamientoDeLaLista(listaProductor);
        System.out.println("** Test 14- Testo el ordenamiento de la tercera Lista**");
        testeoElOrdenamientoDeLaLista(listaCompartida1);
        System.out.println("** Test 15- Testo el ordenamiento de la cuarta Lista**");
        testeoElOrdenamientoDeLaLista(listaCompartida2);
    }

    private static void testeoElOrdenamientoDeLaLista(ListaConcurrente listaConcurrente) {
        try {
			listaConcurrente.quickSort();
		} catch (InterruptedException e) {}
        System.out.println("Sorted numbers: " + listaConcurrente);
        System.out.println("Elements sorted: " + listaConcurrente.size());
        System.out.println(" ");
    }

    private static void testComportamientoDosConsumidoresYUnProductor(ListaConcurrente listaCompartida2) {
        System.out.println("** Test 11- Creo dos consumidor y un productor**");
        Productor productor2 = new Productor(listaCompartida2);
        productor2.start();
        System.out.println("Los elementos son: " + listaCompartida2.getRepresentacion());
        Consumidor consumidor1 = new Consumidor(listaCompartida2);
        Consumidor consumidor2 = new Consumidor(listaCompartida2);
        consumidor1.start();
        consumidor2.start();
        System.out.println(" ");
    }

    private static void testUnConsumidorYUnProductor(ListaConcurrente listaCompartida1) {
        System.out.println("** Test 10- Creo un consumidor y un productor**");
        Productor productor2 = new Productor(listaCompartida1);
        productor2.start();
        System.out.println("Los elementos son: " + listaCompartida1.getRepresentacion());
        Consumidor consumidor1 = new Consumidor(listaCompartida1);
        consumidor1.start();
        System.out.println(" ");
    }

    private static void testInicializarConsumidor(ListaConcurrente listaProductor) {
        System.out.println("** Test 9- Creo un consumidor y consume elementos de la lista**");
        Consumidor consumidor1 = new Consumidor(listaProductor);
        consumidor1.start();
        System.out.println(" ");
    }

    private static void testAgregoOtroProductorYAgrega(ListaConcurrente listaProductor) {
        System.out.println("** Test 08- Creo otro productor y agrega elementos a la lista**");
        Productor productor2 = new Productor(listaProductor);
        productor2.start();
        System.out.println("Los elementos son: " + listaProductor.getRepresentacion());
        testInicializoUnProductor(listaProductor);
        System.out.println(" ");
    }

    private static void testInicializoUnProductorYAgrega(ListaConcurrente listaProductor) {
        System.out.println("** Test 07- Creo un productor y agrega elementos a la lista**");
        Productor productor1 = new Productor(listaProductor);
        productor1.start();
        System.out.println("Los elementos son: " + listaProductor.getRepresentacion());
        testInicializoUnProductor(listaProductor);
        System.out.println(" ");
    }

    private static void testInicializoUnProductor(ListaConcurrente listaProductor) {
        System.out.println("El tamanio de la lista es: " + listaProductor.size());
    }

    private static void testSeteoUnElementoYEstaRepetido(ListaConcurrente listaConcurrente) {
        System.out.println("** Test 06- Seteo un elemento de la lista por otro repetido y no lo agrega**");
        listaConcurrente.set(0,3);
        System.out.println("Los elementos son: " + listaConcurrente.getRepresentacion());
        System.out.println("El tamanio de la lista es: " + listaConcurrente.size());
        System.out.println(" ");
    }

    private static void testSeteoUnElemento(ListaConcurrente listaConcurrente) {
        System.out.println("** Test 05- Seteo un elemento de la lista por otro**");
        listaConcurrente.set(0,10);
        System.out.println("Los elementos son: " + listaConcurrente.getRepresentacion());
        System.out.println("El tamanio de la lista es: " + listaConcurrente.size());
        System.out.println(" ");
    }

    private static void testComprueboSiTengoUnElementoEnLaLista(ListaConcurrente listaConcurrente) {
        System.out.println("** Test 04- Compruebo si contengo un elemento en la lista**");
        System.out.println("Los elementos son: " + listaConcurrente.getRepresentacion());
        System.out.println("Contengo el elemento 3: " + listaConcurrente.contains(3));
        System.out.println(" ");
    }

    private static void testAgregoUnElmentoYestaRepetido(ListaConcurrente listaConcurrente) {
        System.out.println("** Test 03- Agrego un elemento repetido a la lista y nose agrega**");
        listaConcurrente.add(3);
        listaConcurrente.add(2);
        System.out.println("Los elementos son: " + listaConcurrente.getRepresentacion());
        System.out.println("El tamanio de la lista es: " + listaConcurrente.size());
        System.out.println(" ");
    }

    private static void testAgregoUnElementoALaLista(ListaConcurrente listaConcurrente) {
        listaConcurrente.add(2);
        System.out.println("** Test 02- Agrego un elemento a la lista**");
        System.out.println("Los elementos son: " + listaConcurrente.getRepresentacion());
        System.out.println("La lista no es vacia: " + listaConcurrente.isEmpty());
        System.out.println("Devuelvo el elemento agregado: " + listaConcurrente.get(0));
        System.out.println("El tamanio de la lista es: " + listaConcurrente.size());
        System.out.println(" ");
    }

    private static void testListaInicializada(ListaConcurrente listaConcurrente) {
        System.out.println("** Test 01- Inicializo mi lista vacia**");
        System.out.println("Los elementos son: " + listaConcurrente.getRepresentacion());
        System.out.println("La lista efectivamente es vacia: " + listaConcurrente.isEmpty());
        System.out.println(" ");
    }

}