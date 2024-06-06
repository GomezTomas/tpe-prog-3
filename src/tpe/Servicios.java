package tpe;

import tpe.tareas.Tarea;
import tpe.utils.CSVReader;

import java.util.ArrayList;
import java.util.List;

/**
 * NO modificar la interfaz de esta clase ni sus métodos públicos.
 * Sólo se podrá adaptar el nombre de la clase "Tarea" según sus decisiones
 * de implementación.
 */
public class Servicios {

	public Tree tareas;
	public Tree tareasCriticas;
	private ArrayList<Procesador> procesadores;
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
		this.procesadores = reader.getProcesadores();
	}

	/*
     * Complejidad: O(n), donde n es la cantidad de tareas.
     * Se debe recorrer completamente el arbol hasta encontrar el ID solicitado
     * y en el peor caso, el ID no existe.
     * El grupo considero la implementacion de un arbol binario ordenado por ID, ya que si este
     * se encuentra balanceado, la complejidad se reduciria a O(log(n)).
     * Pero en este caso, las tareas estan ordenadas por ID, lo que hace que el arbol
     * se convierta practicamente en una lista, ya que todos los valores se encontrarian de forma
     * lineal.
     */
	public Tarea servicio1(String ID) {
		if (this.tareas.isEmpty()){
			return null;
		} else {
			return servicio1(ID, this.tareas.getRoot());
		}
	}
	private Tarea servicio1(String ID, TreeNode tareaNode) {
		Tarea tmp = null;
		if(tareaNode.getTarea().getId().equals(ID)){
			return tareaNode.getTarea();
		} else {
			if (tareaNode.getLeft() != null){
				tmp = servicio1(ID, tareaNode.getLeft());
			}
			if (tmp != null && tmp.getId().equals(ID)){
				return tmp;
			}
			if (tareaNode.getRight() != null){
				return servicio1(ID, tareaNode.getRight());
			} else {
				return null;
			}
		}
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

	public int cantTareasCriticas(){
		return tareasCriticas.getCantTareasCriticas();
	}
	public int cantTareas(){
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

	/*
	La estrategia utilizada en este algoritmo se basa en generar
	todas las combinaciones posibles entre procesadores y tareas,
	y ver cual de ellas es la que tarda menos tiempo de ejecucion.
	Para reducir la cantidad de estados generados, se utiliza una
	estrategia de poda, donde una vez hallada una posible solucion,
	se guarda tiempo total de ejecucion de dicha solucion. Luego,
	cada vez que se genera un nuevo estado, se analiza el tiempo de
	ejecucion de ese estado en ese momento, y si supera el mejor tiempo
	de ejecucion obtenido hasta el momento, el estado es descartado como
	una posible solucion.
	Ademas, dentro de la poda intervienen otros criterios, como que un
	mismo procesador no puede ejecutar mas de 2 tareas criticas y que los
	procesadores no refrigerados no puedan ejecutar mas de X tiempo de ejecucion
	a las tareas asignadas (este tiempo es dado por parametro [tiempoEjecucion]

	La complejidad de este algoritmo depende de la cantidad de procesadores p,
	y de la cantidad de tareas t. Ya que se generan todas las posibles combinaciones
	entre procesadores y tareas, la cantidad de estados que pueden ser solucion
	se calcularia de la siguiente forma: p^t.
	Como el resto de metodos utilizados dentro del algoritmo no superan la complejidad
	O(t) o O(p), la complejidad temporal resulta en O(p^t)
	 */
	public Solucion backtracking(int tiempoEjecucion){
		List<Tarea> listaTareas = listaTareas(this.tareas); // O(t)
		Solucion solucion = new Solucion(listaTareas); // O(1)
		Estado estado = new Estado(listaTareas); // O(1)
		if (cantTareasCriticas() > this.procesadores.size()){ //O(1)
			return solucion;
		}
		backtracking(solucion, estado, tiempoEjecucion);
		return solucion;
	}
	private void backtracking(Solucion solucion, Estado estado, int tiempoEjecucion){
		if(estado.getNivel() == cantTareas()){ //O(1)
			if (solucion.setValorMinimo(estado.getValor())){ //O(p)
				solucion.setProcesadores(estado.getProcesadores()); //O(p)
			}
		} else {
			for (Procesador proc : this.procesadores){
				solucion.agregarEstadoGenerado();
				Tarea tarea = estado.siguiente(proc);
				if (!poda(solucion, estado, tarea, proc, tiempoEjecucion)){
					backtracking(solucion, estado, tiempoEjecucion);
				}
				estado.remove();
			}
		}
	}
	private boolean poda(Solucion solucion, Estado estado, Tarea tarea, Procesador p, int tiempoEjecucion){
		boolean podar = false;
		if (estado.getValor() >= solucion.getValorMinimo()){
			podar = true;
		}
		if (tarea.isCritica() && p.getEjecutoCritica() > 1){
			podar = true;
		}
		if (!p.isRefrigerado() && tarea.getTiempo() > tiempoEjecucion){
			podar = true;
		}
		return podar;
	}

	public Solucion greedy(){
		//TODO
		return null;
	}
}
