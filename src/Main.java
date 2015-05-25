public class Main {

	public static void main(String[] args) {
		ConcuList buffer = new ConcuList();
		Agrega agregaElementos = new Agrega(buffer);
		agregaElementos.start();
		Agrega agregaElementos2 = new Agrega(buffer);
		agregaElementos2.start();
	}
	
}