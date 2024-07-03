package tpe;

import tpe.tareas.Tarea;
import tpe.utils.CSVReader;

import java.util.*;

/**
 * NO modificar la interfaz de esta clase ni sus métodos públicos.
 * Sólo se podrá adaptar el nombre de la clase "Tarea" según sus decisiones
 * de implementación.
 */
public class Servicios {

	private Tree tareas;
	private Tree tareasCriticas;
	private Tree tareasTiempoEjecucion;
	private ArrayList<Procesador> procesadores;
	private Hashtable<String, Tarea> tareasID;

	/*
     * La complejida de guardar los procesadores es de O(n^2), siendo
     * n la cantidad de procesadores, ya que por cada procesador
     * se inserta un nuevo elemento en una lista.
     * La complejidad de guardar las tareas tambien es de O(m^2), siendo
     * m la cantidad de procesadores. En este caso, sin la decision de implementacion
     * de crear un arbol aparte para las tareas criticas, la complejidad se podria
     * acercar a O(h), donde llegar hasta h en un arbol binario balanceado y completo
     * implicaria una complejidad de O(log2 m) (m = cantidad de tareas). Al no poder
     * asegurar que el arbol se llene de forma balanceada y completa, la complejidad sigue
     * siendo O(m^2)
     *
     * De forma similar a la del arbol de tareas criticas, para implementar el algoritmo
     * de greedy se agrego la implementacion de un arbol ordenado por el tiempo de ejecucion
     * de las tareas, que afecta en cuanto a espacio utilizado y tiempo de ejecucion, pero no
     * afecta la complejidad algoritmica.
     *
     * Se agrego la implementacion de una Hashtable para guardar las tareas segun su id y asi
     * mejorar la eficiencia del servicio 1. Insertar un elemento en una hastable suele tener
     * una complejidad constante, pero en caso de que tenga que crecer la estructura, nos
     * acercariamos a una complejidad de O(m). Sin embargo, sigue siendo menor que la complejidad
     * de insertarlas en el arbol de tareas criticas.
     *
     * Entonces, la complejidad total seria O(m^2 + n^2), pero podemos asumir que la cantidad
     * de tareas, en general, va a ser mayor o igual a la cantidad de procesadores, por lo que
     * decidimos expresar la complejidad solo en termino de la cantidad de tareas, por lo tanto,
     * complejidad = O(m^2), m = cantidad de tareas.
     */
	public Servicios(String pathProcesadores, String pathTareas)
	{
		CSVReader reader = new CSVReader();
		reader.readProcessors(pathProcesadores);
		reader.readTasks(pathTareas);
		this.tareas = reader.getTareas();
		this.tareasCriticas = reader.getTareasCriticas();
		this.tareasTiempoEjecucion = reader.getTareasTiempoEjecucion();
		this.procesadores = reader.getProcesadores();
		this.tareasID = reader.getTareasID();
	}

	/*
     * Complejidad: O(1), ya que la complejidad de consultar una hashtable
     * generalmente es constante y no depende de la cantidad de elementos.
     */
	public Tarea servicio1(String ID) {
		return this.tareasID.get(ID);
	}

	/*
	 * Complejidad: O(n), donde n es la cantidad de tareas.
	 * El grupo decidio implementar un arbol en el cual en su rama mas izquierda
	 * estan todas las tareas que no son criticas, y en su rama mas derecha las
	 * tareas que son criticas. En el peor de los casos, habria que retornar todas las tareas
	 * si coincide, por ejemplo, que todas las tareas son criticas y se solicita el listado de
	 * las tareas criticas. Con esta implementacion se busca optimizar la busqueda
	 * evitando analizar tareas no deseadas, teniendo que utilizar mas espacio.
	 */
	public List<Tarea> servicio2(boolean esCritica) {
		List<Tarea> listaCriticas = new ArrayList<>();
		if(!this.tareasCriticas.isEmpty()){
			TreeNode root = this.tareasCriticas.getRoot();
			if(esCritica == root.getTarea().isCritica()){
				listaCriticas.add(root.getTarea());
			}
			if(esCritica){
				if (root.getRight() != null){
					servicio2(listaCriticas, esCritica, root.getRight());
				}
			} else {
				if (root.getLeft() != null){
					servicio2(listaCriticas, esCritica, root.getLeft());
				}
			}
		}
		return listaCriticas;
	}
	private void servicio2(List<Tarea> listaCriticas, boolean esCritica, TreeNode actual){
		listaCriticas.add(actual.getTarea());
		if (esCritica && actual.getRight() != null){
			servicio2(listaCriticas, esCritica, actual.getRight());
		} else if (!esCritica && actual.getLeft() != null){
			servicio2(listaCriticas, esCritica, actual.getLeft());
		}
	}

	private int cantTareasCriticas(){
		return tareasCriticas.getCantTareasCriticas();
	}
	private int cantTareas(){
		return tareas.getCantTareas();
	}

    /*
     * Complejidad: O(n), donde n es la cantidad de tareas.
	 * En la implementacion de este servicio, el arbol esta ordenado por prioridad, asi
	 * que en el caso de que todas las tareas se encuentren dentro de la cota
	 * superior e inferior (peor caso) se tienen que recorrer todos los elementos del arbol.
	 * La optimizacion de este servicio ocurre al no analizar aquellos hijos menores que
	 * un valor que sea menor que el limite inferior, y lo mismo para los valores mayores
	 * al limite superior.
     */
	public List<Tarea> servicio3(int prioridadInferior, int prioridadSuperior) {
		List<Tarea> tareasAcotadas = new ArrayList<>();
		if (!this.tareas.isEmpty()){
			TreeNode root = this.tareas.getRoot();
			servicio3(prioridadInferior, prioridadSuperior, root, tareasAcotadas);
		}
		return tareasAcotadas;
	}
	private void servicio3(int prioridadInferior, int prioridadSuperior, TreeNode actual, List<Tarea> tareasAcotadas) {
		Tarea tareaActual = actual.getTarea();
		int prioridadActual = tareaActual.getPrioridad();
		if (prioridadInferior <= prioridadActual && prioridadSuperior >= prioridadActual){
			tareasAcotadas.add(tareaActual);
		}
		if (prioridadInferior < prioridadActual){
			if(actual.getLeft() != null){
				servicio3(prioridadInferior, prioridadSuperior, actual.getLeft(), tareasAcotadas);
			}
		}
		if (prioridadSuperior > prioridadActual){
			if (actual.getRight() != null){
				servicio3(prioridadInferior, prioridadSuperior, actual.getRight(), tareasAcotadas);
			}
		}
	}

	private List<Tarea> listaTareas(Tree tareas){
		List<Tarea> listaTareas = new ArrayList<>();
		listaTareas(tareas.getRoot(), listaTareas);
		return listaTareas;
	}
	private void listaTareas(TreeNode tarea, List<Tarea> listaTareas){
		if (tarea.getRight() != null){
			listaTareas(tarea.getRight(), listaTareas);
		}
		listaTareas.add(tarea.getTarea());
		if (tarea.getLeft() != null){
			listaTareas(tarea.getLeft(), listaTareas);
		}
	}
	private void resetearProcesadores(){
		for (Procesador proc : procesadores){
			proc.resetCritica();
			proc.setTiempoEjecucion(0);
		}
	}

	/*
	* La estrategia utilizada en este algoritmo se basa en generar
	* todas las combinaciones posibles entre procesadores y tareas,
	* y ver cual de ellas es la que tarda menos tiempo de ejecucion.
	* Para reducir la cantidad de estados generados, se utiliza una
	* estrategia de poda, donde una vez hallada una posible solucion,
	* se guarda tiempo total de ejecucion de dicha solucion. Luego,
	* cada vez que se genera un nuevo estado, se analiza el tiempo de
	* ejecucion de ese estado en ese momento, y si supera el mejor tiempo
	* de ejecucion obtenido hasta el momento, el estado es descartado como
	* una posible solucion.
	* Ademas, dentro de la poda intervienen otros criterios, como que un
	* mismo procesador no puede ejecutar mas de 2 tareas criticas y que los
	* procesadores no refrigerados no puedan ejecutar mas de X tiempo de ejecucion
	* a las tareas asignadas (este tiempo es dado por parametro [tiempoEjecucion])
	*
	* En la solucion se guardan 2 listas, una de procesadores y otra de tareas, de las cuales
	* la tarea que se encuentre en la posicion i, sera resuelta por el procesador ubicada
	* en la posicion i.
	*
	* La complejidad de este algoritmo depende de la cantidad de procesadores [p],
	* y de la cantidad de tareas [t]. Ya que se generan todas las posibles combinaciones
	* entre procesadores y tareas, la cantidad de estados que pueden ser solucion
	* se calcularia de la siguiente forma: p^t.
	* Como el resto de metodos utilizados dentro del algoritmo no superan la complejidad
	* O(t) o O(p), la complejidad temporal resulta en O(p^t)
	*/
	public Solucion backtracking(int tiempoEjecucion){
		resetearProcesadores();
		List<Tarea> listaTareas = listaTareas(this.tareas);
		Solucion solucion = new Solucion(listaTareas, "Backtracking", this.procesadores); // O(1)
		if (cantTareasCriticas() > this.procesadores.size() * 2){
			return solucion;
		}
		Estado estado = new Estado(listaTareas, this.procesadores);
		backtracking(solucion, estado, tiempoEjecucion);
		return solucion;
	}
	private void backtracking(Solucion solucion, Estado estado, int tiempoEjecucion){
		if(estado.getNivel() == cantTareas()){
			if (solucion.setTiempoEjecucionMinimo(estado.getTiempoEjecucion())){
				solucion.setProcesadores(estado.getProcesadores());
			}
		} else {
			for (Procesador proc : this.procesadores){
				solucion.agregarEstadoGenerado();
				Tarea tarea = estado.siguiente(proc);
				if (!poda(solucion, estado, tarea, proc, tiempoEjecucion)){
					proc.sumarTiempo(tarea.getTiempo());
					backtracking(solucion, estado, tiempoEjecucion);
					proc.restarTiempo(tarea.getTiempo());
				}
				estado.remove();
			}
		}
	}
	private boolean poda(Solucion solucion, Estado estado, Tarea tarea, Procesador p, int tiempoEjecucion){
		boolean podar = false;
		if (estado.getTiempoEjecucion() >= solucion.getTiempoEjecucionMinimo()){
			podar = true;
		}
		if (tarea.isCritica() && p.getEjecutoCritica() > 2){
			podar = true;
		}
		if (!p.isRefrigerado() && (estado.getTiempoEjecucion(p) + tarea.getTiempo()) > tiempoEjecucion){
			podar = true;
		}
		return podar;
	}

	private Procesador mejorProcesador(List<Procesador> procesadores, List<Tarea> listaTareas, int tiempoEjecucion, Solucion solucion, Estado estado){
		boolean procesadorElegido = false;
		Procesador factible = null;
		Procesador mejorProcesador = null;
		while(!procesadorElegido && !procesadores.isEmpty()){
			int i = 0;
			int j = 0;
			int sum = Integer.MAX_VALUE;
			for (Procesador p : procesadores){
				if (p.getTiempoEjecucion() < sum){
					factible = p;
					sum = p.getTiempoEjecucion();
					j = i;
				}
				solucion.agregarEstadoGenerado();
				i++;
			}
			if (puedeEjecutar(factible, listaTareas.get(0), tiempoEjecucion, estado)){
				procesadorElegido = true;
				mejorProcesador = factible;
			} else {
				procesadores.remove(j);
			}
		}
		return mejorProcesador;
	}

	/*
	 * Para implementar el algoritmo, utilizamos el siguiente criterio:
	 * Ordenamos las tareas de mayor a menor, utilizando el tiempo de ejecucion
	 * como criterio de ordenamiento.
	 * Luego, para la asignacion de los procesadores, elegimos el procesador
	 * que tenga menor tiempo de ejecucion hasta el momento, ya que el resultado
	 * final de la ejecucion va a ser igual al tiempo de ejecucion del procesador
	 * que mas tarda. (El procesador con mayor tiempo de ejecucion).
	 *
	 * La complejidad del algoritmo depende tanto de la cantidad de tareas [t],
	 * como de la cantidad de procesadores [p].
	 * Ya que en el peor caso, por cada tarea se revisan todos los procesadores
	 * la complejida algoritmica es del orden de O(p*t)
	 */
	public Solucion greedy(int tiempoEjecucion){
		resetearProcesadores();
		List<Tarea> listaTareas = listaTareas(this.tareasTiempoEjecucion);
		Solucion solucion = new Solucion(listaTareas, "Greedy2", this.procesadores);
		if (cantTareasCriticas() > this.procesadores.size() * 2){
			return solucion;
		}
		Estado estado = new Estado(listaTareas, this.procesadores);
		while (!listaTareas.isEmpty()){
			List<Procesador> procesadores = new ArrayList<>(this.procesadores);
			if(procesadores.size() < this.procesadores.size()){
				procesadores = new ArrayList<>(this.procesadores);
			}
			Procesador mejorProcesador = mejorProcesador(procesadores, listaTareas, tiempoEjecucion, solucion, estado);
			if (mejorProcesador == null) {
				System.out.println("1278936128763918723689126318927638912");
				return solucion;
			} else {
				estado.addProcesador(mejorProcesador, listaTareas.get(0));
				Tarea tarea = listaTareas.remove(0);
				mejorProcesador.sumarTiempo(tarea.getTiempo());
			}

		}
		solucion.setProcesadores(estado.getProcesadores());
		solucion.setTiempoEjecucionMinimo(estado.getTiempoEjecucion());
		return solucion;
	}

	private boolean puedeEjecutar(Procesador procesador, Tarea tarea, int tiempoEjecucion, Estado estado){
		boolean puede = true;
		if (tarea.isCritica() && procesador.getEjecutoCritica() > 1){
			puede = false;
		}
		if (!procesador.isRefrigerado() && (estado.getTiempoEjecucion(procesador) + tarea.getTiempo()) > tiempoEjecucion){
			puede = false;
		}
		return puede;
	}
}
