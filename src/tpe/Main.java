package tpe;

public class Main {

	public static void main(String[] args) {
		Servicios servicios = new Servicios("./src/tpe/datasets/Procesadores.csv", "./src/tpe/datasets/Tareas2.csv");

		System.out.println(servicios.backtracking(200));
		System.out.println(servicios.greedy(200));

		System.out.println("-----------------------");
		System.out.println(servicios.servicio1("T6"));

		System.out.println(servicios.servicio2(false));
		System.out.println(servicios.servicio3(30, 80));
	}
}
