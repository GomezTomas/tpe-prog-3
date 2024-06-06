package tpe;

import tpe.tareas.Tarea;

import java.util.ArrayList;
import java.util.List;

public class Estado {

    private List<Procesador> procesadores;
    private List<Tarea> tareas;

    private int nivel;

    public Estado(List<Tarea> tareas) {
        this.procesadores = new ArrayList<>();
        this.nivel = 0;
        this.tareas = tareas;
    }

    public Tarea siguiente(Procesador proc) {
        this.procesadores.add(this.nivel, proc);
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
            this.procesadores.get(this.nivel).removeCritica();
        }
        this.procesadores.remove(this.nivel);
    }

    public int getNivel(){
        return this.nivel;
    }

    public List<Procesador> getProcesadores(){
        return this.procesadores;
    }

    public int getValor(){
        int i = 0;
        int valor = 0;
        while (i < this.procesadores.size()) {
            int valorTarea = this.tareas.get(i).getTiempo();
            int valorProcesador = this.procesadores.get(i).getValor();
            this.procesadores.get(i).setValor(valorProcesador + valorTarea);
            if (this.procesadores.get(i).getValor() > valor) {
                valor = this.procesadores.get(i).getValor();
            }
            i++;
        }
        for (Procesador proc : this.procesadores) {
            proc.setValor(0);
        }
        return valor;
    }

}
