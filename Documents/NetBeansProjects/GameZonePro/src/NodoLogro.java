public class NodoLogro {
    String nombre;
    String descripcion;
    boolean desbloqueado;
    NodoLogro siguiente;

    public NodoLogro(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.desbloqueado = false;
        this.siguiente = null;
    }
}
