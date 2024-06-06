package tpe;

import java.util.ArrayList;

public class Main {

	public static void main(String args[]) {
		Servicios servicios = new Servicios("./src/tpe/datasets/Procesadores.csv", "./src/tpe/datasets/Tareas.csv");
//		long start = System.nanoTime();
		System.out.println(servicios.backtracking(50));
//		long end = System.nanoTime();
//		long elapsedTime = (end - start)/1000000;
//		System.out.println(elapsedTime);
//		System.out.println(servicios.servicio1("T1"));
//		System.out.println(servicios.servicio2(false));
//		System.out.println(servicios.servicio3(40, 80));
	}
}
