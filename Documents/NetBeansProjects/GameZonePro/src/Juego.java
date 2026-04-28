public class Juego {
    String codigo;
    String nombre;
    String genero;
    String precio;
    String plataforma;
    int stock;
    String descripcion;
    Juego siguiente;
    
    public Juego(String codigo, String nombre, String genero, String precio, 
                 String plataforma, int stock, String descripcion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.genero = genero;
        this.precio = precio;
        this.plataforma = plataforma;
        this.stock = stock;
        this.descripcion = descripcion;
        this.siguiente = null;
    }
}