package tpe;

import tpe.tareas.Tarea;

import java.util.ArrayList;
import java.util.List;

public class Solucion {
    private List<Tarea> tareas;
    private List<Procesador> procesadores;
    private int cantidadEstadosGenerados;
    private int valorMinimo;

    public Solucion(List<Tarea> tareas){
        this.tareas = tareas;
        this.procesadores = new ArrayList<>();
        this.cantidadEstadosGenerados = 0;
        this.valorMinimo = Integer.MAX_VALUE;
    }

    public void agregarEstadoGenerado(){
        this.cantidadEstadosGenerados++;
    }

    public boolean setValorMinimo(int valorMinimo){
        if (valorMinimo < this.valorMinimo) {
            this.valorMinimo = valorMinimo;
            return true;
        }
        return false;
    }

    public int getValorMinimo(){
        return this.valorMinimo;
    }

    public void setProcesadores(List<Procesador> procesadores){
        this.procesadores = new ArrayList<>(procesadores);
    }

    @Override
    public String toString() {
        if (this.procesadores.isEmpty()) {
            return "No hay suficientes procesadores que cumplan las condiciones necesarias";
        }
        int i = 0;
        String solucion = "La solucion es: \n";
        while(i < this.procesadores.size()){
            solucion += String.format("Proc:%s, Tarea:%s", this.procesadores.get(i), this.tareas.get(i));
            i++;
        }
        solucion += String.format("El tiempo de ejecucion de las tareas es: %s\n", this.valorMinimo);
        solucion += String.format("Y la cantidad de estados generados es: %s\n", this.cantidadEstadosGenerados);
        return solucion;
    }
}
