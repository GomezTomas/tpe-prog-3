package tpe;

import tpe.tareas.Tarea;

import java.util.ArrayList;
import java.util.List;

public class Estado {

    private List<Procesador> procesadoresEstado;
    private List<Procesador> procesadoresOriginal;
    private List<Tarea> tareas;

    private int nivel;

    public Estado(List<Tarea> tareas, List<Procesador> procesadoresOriginal) {
        this.procesadoresEstado = new ArrayList<>();
        this.procesadoresOriginal = new ArrayList<>(procesadoresOriginal);
        this.nivel = 0;
        this.tareas = new ArrayList<>(tareas);
    }

    public Tarea siguiente(Procesador proc) {
        this.procesadoresEstado.add(this.nivel, proc);
        Tarea tarea = this.tareas.get(this.nivel);
        if (tarea.isCritica()){
            proc.ejecutarCritica();
        }
        this.nivel++;
        return tarea;
    }

    public void remove(){
        this.nivel--;
        if (this.tareas.get(this.nivel).isCritica()){
            this.procesadoresEstado.get(this.nivel).removeCritica();
        }
        this.procesadoresEstado.remove(this.nivel);
    }

    public void addProcesador(Procesador proc, Tarea tarea){
        if (tarea.isCritica()){
            proc.ejecutarCritica();
        }
        this.procesadoresEstado.add(proc);
    }

    public int getNivel(){
        return this.nivel;
    }

    public List<Procesador> getProcesadores(){
        return this.procesadoresEstado;
    }

    public int getTiempoEjecucion(){
        int tiempoEjecucion = 0;
        for (Procesador proc : this.procesadoresOriginal){
            int tiempoProc = getTiempoEjecucion(proc);
            if (tiempoProc > tiempoEjecucion){
                tiempoEjecucion = tiempoProc;
            }
        }
        return tiempoEjecucion;
    }

    public int getTiempoEjecucion(Procesador proc){
        int i = 0;
        int tiempoEjecucion = 0;
        while (i < this.procesadoresEstado.size()) {
            if (this.procesadoresEstado.get(i).equals(proc)){
                int tiempoEjecucionTarea = this.tareas.get(i).getTiempo();
                int tiempoEjecucionProcesador = this.procesadoresEstado.get(i).getTiempoEjecucion();
                this.procesadoresEstado.get(i).setTiempoEjecucion(tiempoEjecucionProcesador + tiempoEjecucionTarea);
                if (this.procesadoresEstado.get(i).getTiempoEjecucion() > tiempoEjecucion) {
                    tiempoEjecucion = this.procesadoresEstado.get(i).getTiempoEjecucion();
                }
            }
            i++;
        }
        proc.setTiempoEjecucion(0);
        return tiempoEjecucion;
    }

}
