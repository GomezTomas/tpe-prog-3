package tpe.utils;


import tpe.Procesador;
import tpe.tareas.Tarea;
import tpe.tareas.TareaCritica;
import tpe.Tree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class CSVReader {

	private Tree tareas;
	private Tree tareasCriticas;
	private ArrayList<Procesador> procesadores;

	public CSVReader() {
		this.tareas = new Tree();
		this.tareasCriticas = new Tree();
		this.procesadores = new ArrayList<>();
	}
	
	public void readTasks(String taskPath) {
		
		// Obtengo una lista con las lineas del archivo
		// lines.get(0) tiene la primer linea del archivo
		// lines.get(1) tiene la segunda linea del archivo... y así
		ArrayList<String[]> lines = this.readContent(taskPath); //O(N) n = elementos archivo
		for (String[] line: lines) { //O(N) n = elementos archivo
			// Cada linea es un arreglo de Strings, donde cada posicion guarda un elemento
			String id = line[0].trim();
			String nombre = line[1].trim();
			Integer tiempo = Integer.parseInt(line[2].trim());
			Boolean critica = Boolean.parseBoolean(line[3].trim());
			Integer prioridad = Integer.parseInt(line[4].trim());
			// Aca instanciar lo que necesiten en base a los datos leidos
			Tarea tarea = new Tarea(id, nombre, tiempo, critica, prioridad);
			TareaCritica tareaCritica = new TareaCritica(id, nombre, tiempo, critica, prioridad);
			this.tareas.add(tarea);
			this.tareasCriticas.add(tareaCritica);
		}
	}
	
	public void readProcessors(String processorPath) {
		
		// Obtengo una lista con las lineas del archivo
		// lines.get(0) tiene la primer linea del archivo
		// lines.get(1) tiene la segunda linea del archivo... y así
		ArrayList<String[]> lines = this.readContent(processorPath);
		for (String[] line: lines) {
			// Cada linea es un arreglo de Strings, donde cada posicion guarda un elemento
			String id = line[0].trim();
			String codigo = line[1].trim();
			Boolean refrigerado = Boolean.parseBoolean(line[2].trim());
			Integer anio = Integer.parseInt(line[3].trim());
			// Aca instanciar lo que necesiten en base a los datos leidos
			Procesador proc = new Procesador(id, codigo, refrigerado, anio);
			this.procesadores.add(proc);
		}
	}

	private ArrayList<String[]> readContent(String path) {
		ArrayList<String[]> lines = new ArrayList<String[]>();

		File file = new File(path);
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				line = line.trim();
				lines.add(line.split(";"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (bufferedReader != null)
				try {
					bufferedReader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		}
		
		return lines;
	}

	public Tree getTareas(){
		return this.tareas;
	}
	public Tree getTareasCriticas(){
		return this.tareasCriticas;
	}
	public ArrayList<Procesador> getProcesadores(){
		return new ArrayList<Procesador>(this.procesadores);
	}
}
