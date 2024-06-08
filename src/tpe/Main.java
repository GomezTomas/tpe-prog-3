package tpe;

public class Main {

	public static void main(String[] args) {
		Servicios servicios = new Servicios("./src/tpe/datasets/Procesadores.csv", "./src/tpe/datasets/Tareas.csv");

		System.out.println(servicios.backtracking(70));
		System.out.println(servicios.greedy(70));
		System.out.println("-----------------------");
		System.out.println(servicios.servicio1("T1"));
		System.out.println(servicios.servicio2(true));
		System.out.println(servicios.servicio3(40, 80));
	}
}
