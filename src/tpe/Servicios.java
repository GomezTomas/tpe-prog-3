package tpe;

import tpe.utils.CSVReader;

import java.util.ArrayList;
import java.util.List;

/**
 * NO modificar la interfaz de esta clase ni sus métodos públicos.
 * Sólo se podrá adaptar el nombre de la clase "Tarea" según sus decisiones
 * de implementación.
 */
public class Servicios {

	private ArrayList<Tarea> tareas;
	private ArrayList<Procesador> procesadores;
	/*
     * Llamamos n a la cantidad de tareas que se encuentran en el archivo csv
     * y m a la cantidad de procesadores. Asumiendo que n = m, la complejidad
     * del algoritmo es de O(n). En caso de que n > m, seguiria
     * conservando esa complejidad, en caso de que m > n, la complejidad
     * seria de O(m).
     * Esto se debe a que el CSVReader recorre todos los elementos de los archivos ".csv"
     * y luego se hace de nuevo un recorrido elemento por elemento, resultando en
     * una complejidad O(2n) = O(n).
     */
	public Servicios(String pathProcesadores, String pathTareas)
	{
		CSVReader reader = new CSVReader(); //O(1)
		this.procesadores = reader.readProcessors(pathProcesadores); //O(N) n = cantProcesadores
		this.tareas = reader.readTasks(pathTareas); //O(M) m = cantTareas
//		System.out.println(this.tareas);
//		System.out.println(this.procesadores);
	}

	/*
     * Complejidad: O(n), donde n es la cantidad de tareas que existen en el arraylist tareas.
     * ya que en el peor caso, buscamos la ultima tarea o la tarea no existe y se debe recorrer
     * el arraylist completo.
     */
	public Tarea servicio1(String ID) {
//		es necesario hacer el lower o consideramos que es un id distinto??
        String id = ID.toLowerCase();
		int i = 0;
		Tarea tarea = null;
		while (i < this.tareas.size()) {
			if (this.tareas.get(i).getId().toLowerCase().equals(id)) {
				tarea = this.tareas.get(i);
				break;
			}
			i++;
		}
		return tarea;
	}

	/*
	 * Complejidad: O(n), donde n es la cantidad de tareas que existen en el arraylist tareas.
	 * ya que debemos recorrer toda la lista para analizar si cada tarea es critica o no.
	 */
	public List<Tarea> servicio2(boolean esCritica) {
		List<Tarea> tareasCriticas = new ArrayList<>();
		for (Tarea tarea : this.tareas) {
			if (tarea.isCritica() == esCritica) {
				tareasCriticas.add(tarea);
			}
		}
		return tareasCriticas;
	}

    /*
     * Complejidad: O(n), donde n es la cantidad de tareas que existen en el arraylist tareas.
	 * ya que debemos recorrer toda la lista para analizar si la prioridad de cada tarea
	 * se encuentra dentro de los limites.
     */
	public List<Tarea> servicio3(int prioridadInferior, int prioridadSuperior) {
		List<Tarea> tareas = new ArrayList<>();
		for (Tarea tarea : this.tareas) {
			if(prioridadInferior < tarea.getPrioridad() && tarea.getPrioridad() < prioridadSuperior){
				tareas.add(tarea);
			}
		}
		return tareas;
	}

}
