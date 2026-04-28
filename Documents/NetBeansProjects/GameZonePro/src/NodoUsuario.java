public class NodoUsuario {
    String nombre;
    int xp;
    NodoUsuario siguiente;
    
    public NodoUsuario(String nombre, int xp) {
        this.nombre = nombre;
        this.xp = xp;
        this.siguiente = null;
    }
}
