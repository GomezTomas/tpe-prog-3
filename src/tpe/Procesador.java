package tpe;

public class Procesador {
    private String id;
    private String codigo;
    private Boolean refrigerado;
    private Integer anio;

    public Procesador(String id, String codigo, Boolean refrigerado, Integer anio) {
        this.id = id;
        this.codigo = codigo;
        this.refrigerado = refrigerado;
        this.anio = anio;
    }

    public String toString() {
        return String.format("[id: %s, codigo: %s, refrigerado: %s, anio: %s] \n", this.id, this.codigo, this.refrigerado, this.anio);
    }

}
