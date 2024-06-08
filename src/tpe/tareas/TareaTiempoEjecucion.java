package tpe.tareas;

public class TareaTiempoEjecucion extends Tarea{

    public TareaTiempoEjecucion(String id, String nombre, Integer tiempo, Boolean critica, Integer prioridad) {
        super(id, nombre, tiempo, critica, prioridad);
    }
    public int compareTo(Tarea o) {
        return this.getTiempo().compareTo(o.getTiempo());
    }
}
