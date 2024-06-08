package tpe;

import tpe.tareas.Tarea;

import java.util.ArrayList;
import java.util.List;

public class Solucion {
    private List<Tarea> tareas;
    private List<Procesador> procesadoresSolucion;
    private List<Procesador> procesadores;
    private int cantidadEstadosGenerados;
    private int tiempoEjecucionMinimo;
    private String algoritmo;

    public Solucion(List<Tarea> tareas, String algoritmo, List<Procesador> procesadores) {
        this.tareas = new ArrayList<>(tareas);
        this.procesadoresSolucion = new ArrayList<>();
        this.procesadores = new ArrayList<>(procesadores);
        this.cantidadEstadosGenerados = 0;
        this.tiempoEjecucionMinimo = Integer.MAX_VALUE;
        this.algoritmo = algoritmo;
    }

    public void agregarEstadoGenerado(){
        this.cantidadEstadosGenerados++;
    }

    public boolean setTiempoEjecucionMinimo(int valorMinimo){
        if (valorMinimo < this.tiempoEjecucionMinimo) {
            this.tiempoEjecucionMinimo = valorMinimo;
            return true;
        }
        return false;
    }

    public int getTiempoEjecucionMinimo(){
        return this.tiempoEjecucionMinimo;
    }

    public void setProcesadores(List<Procesador> procesadores){
        this.procesadoresSolucion = new ArrayList<>(procesadores);
    }

    @Override
    public String toString() {
        if (this.procesadoresSolucion.isEmpty()) {
            return "No hay suficientes procesadores que cumplan las condiciones necesarias";
        }
        String solucion = String.format("%s\nLa solucion es: \n", this.algoritmo);
        for (Procesador proc : procesadores) {
            int i = 0;
            solucion += String.format("Procesador %s\n", proc);
            while(i < this.procesadoresSolucion.size()){
                if (proc.equals(this.procesadoresSolucion.get(i))) {
                    solucion += String.format("Tarea:%s" , this.tareas.get(i));
                }
                i++;
            }

        }
        solucion += String.format("El tiempo de ejecucion de las tareas es: %s\n", this.tiempoEjecucionMinimo);
        if (algoritmo.equals("Greedy")){
            solucion += String.format("Y la cantidad de candidatos considerados es: %s\n", this.cantidadEstadosGenerados);
        } else {
            solucion += String.format("Y la cantidad de estados generados es: %s\n", this.cantidadEstadosGenerados);
        }
        return solucion;
    }
}
