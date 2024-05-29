package tpe.tareas;

public class TareaCritica extends Tarea {
    public TareaCritica(String id, String nombre, Integer tiempo, Boolean critica, Integer prioridad) {
        super(id, nombre, tiempo, critica, prioridad);
    }

    @Override
    public int compareTo(Tarea o) {
        if (this.isCritica()){
            return 1;
        } else {
            return -1;
        }
    }
}
