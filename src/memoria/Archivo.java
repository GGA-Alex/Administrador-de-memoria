
package memoria;

public class Archivo implements Comparable<Archivo> {
    
    String nombre;
    long tamanio;

    public Archivo(String nombre, long tamanio) {
        this.nombre = nombre;
        this.tamanio = tamanio;
    }

    public String getNombre() {
        return nombre;
    }
     
    public long getTamanio() {
        return tamanio;
    }
    

    @Override
    public String toString() {
        return "Archivo{" + "nombre=" + nombre + ", tamaño=" + tamanio + '}';
    }

    @Override
    public int compareTo(Archivo e){
        if(e.getTamanio()>tamanio){
            return -1;
        }else if(e.getTamanio()==tamanio){
            return 0;
        }else{
            return 1;
        }
    }
}
