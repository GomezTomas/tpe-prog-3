package tpe.tareas;

public class Tarea implements Comparable<Tarea>{
    private String id;
    private String nombre;
    private Integer tiempo;
    private Boolean critica;
    private Integer prioridad;

    public Tarea(String id, String nombre, Integer tiempo, Boolean critica, Integer prioridad) {
        this.id = id;
        this.nombre = nombre;
        this.tiempo = tiempo;
        this.critica = critica;
        this.prioridad = prioridad;
    }

    @Override
    public String toString() {
        return String.format("[id: %s, tiempoEjecucion: %s, critica: %s, prioridad: %s]\n", this.id, this.tiempo, this.critica, this.prioridad);
    }

    public String getId() {
        return this.id;
    }
    public Boolean isCritica() {
        return this.critica;
    }
    public Integer getPrioridad() {
        return this.prioridad;
    }
    public Integer getTiempo() {
        return this.tiempo;
    }

    @Override
    public int compareTo(Tarea o) {
        return this.getPrioridad().compareTo(o.getPrioridad());
    }
}

