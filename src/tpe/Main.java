package tpe;

public class Main {

	public static void main(String args[]) {
		Servicios servicios = new Servicios("./src/tpe/datasets/Procesadores.csv", "./src/tpe/datasets/Tareas.csv");
		System.out.println(servicios.servicio1("T4"));
		System.out.println(servicios.servicio2(false));
		System.out.println(servicios.servicio3(40, 80));
	}
}
