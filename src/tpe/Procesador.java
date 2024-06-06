package tpe;

public class Procesador {
    private String id;
    private String codigo;
    private Boolean refrigerado;
    private Integer anio;

    private int valor;
    private int ejecutoCritica;

    public Procesador(String id, String codigo, Boolean refrigerado, Integer anio) {
        this.id = id;
        this.codigo = codigo;
        this.refrigerado = refrigerado;
        this.anio = anio;

        this.valor = 0;
        this.ejecutoCritica = 0;
    }

    public int getValor(){
        return this.valor;
    }
    public void setValor(int val){
        this.valor = val;
    }
    public void ejecutarCritica(){
        this.ejecutoCritica++;
    }
    public void removeCritica(){
        this.ejecutoCritica--;
    }
    public int getEjecutoCritica(){
        return this.ejecutoCritica;
    }
    public boolean isRefrigerado(){
        return this.refrigerado;
    }

    public String toString() {
        return String.format("id: %s", this.id);
    }
}
