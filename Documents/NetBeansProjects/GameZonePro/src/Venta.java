import java.time.LocalDateTime;

public class Venta {
    String nombre;
    String fecha;
    int precio;
    int cantidad;

    public Venta(String nombre, int precio, int cantidad) {
        this.nombre = nombre;
        this.fecha = java.time.LocalDateTime.now().toString();
        this.precio = precio;
        this.cantidad = cantidad;
    }
}