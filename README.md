# Cambios en la reentrega

## Servicio 1:

Se implementó una hastable a la que se accede mediante el id de la tarea.

```java
public class Servicios{
    private Hashtable<String, Tarea> tareasID;

    public Servicios(...){
      this.tareasID = reader.getTareasID();
    }
    public Tarea servicio1(String ID) {
    		return this.tareasID.get(ID);
    }
}
```

## Servicio 3
El problema del servicio 3 que no devolvía las tareas con prioridad repetida se debía a que al momento de insertar tareas en el arbol de prioridad, solo se tenia en cuenta si el valor era mayor o menor, por los que los valores iguales no se agregaban al arbol. Al solucionar este problema, el servicio 3 funciona como corresponde.

## Backtracking y Greedy

Primero, debido a un error de interpretacion del enunciado, modificamos la cantidad de tareas críticas que podia resolver cada procesador, que antes era de una sola tarea, y ahora se pueden asignar 2.

También se modificó la forma de obtener el tiempo de ejecución de cada procesador, asignandole un atributo al mismo.

```java
public class Procesador{

    private int tiempoEjecucion;

    public void sumarTiempo(int tiempoEjecucion){
        this.tiempoEjecucion += tiempoEjecucion;
    }
    public void restarTiempo(int tiempoEjecucion){
        this.tiempoEjecucion -= tiempoEjecucion;
    }
}

Public class Servicios {
    //backtracking
    if (!poda(solucion, estado, tarea, proc, tiempoEjecucion)){
        proc.sumarTiempo(tarea.getTiempo());
        backtracking(solucion, estado, tiempoEjecucion);
        proc.restarTiempo(tarea.getTiempo());
    }

    //greedy
    mejorProcesador.sumarTiempo(tarea.getTiempo());
}
```

Además, se solucionó el problema de que no se tenía en cuenta si el procesador no refrigerado iba a tener un tiempo de ejecucion mayor al permitido antes de agregar la tarea.

```java
public class Servicios{
    private boolean poda(...){
        if (!p.isRefrigerado() && (estado.getTiempoEjecucion(p) + tarea.getTiempo()) > tiempoEjecucion){
            podar = true;
        }
    }
    private boolean puedeEjecutar(...){
        if (!procesador.isRefrigerado() && (estado.getTiempoEjecucion(procesador) + tarea.getTiempo()) > tiempoEjecucion){
            puede = false;
        }
        return puede;
    }
}
```

Por ultimo, se cambió la forma de elegir los procesadores que ejecutan las tareas en el algoritmo de greedy. Ahora, por cada tarea, se analiza cual es el procesador que lleva menor tiempo de ejecución en ese momento, y si cumple los requerimientos ejecuta la tarea. Si no cumple los requerimientos, se busca otro procesador, y si no encuentra ningun procesador, el algoritmo no encuentra solución.

La forma encontrar el mejor procesador se encuentra en el metodo:

```java
public class Servicios{
    private Procesador mejorProcesador(...)
}
```
