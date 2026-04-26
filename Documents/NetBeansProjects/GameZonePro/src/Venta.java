import java.time.LocalDateTime;

public class Venta {
    String nombre;
    String fecha;
    int precio;
    int cantidad;

    public Venta(String nombre, int precio, int cantidad) {
        this.nombre = nombre;
        this.fecha = LocalDateTime.now().toString();
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public String toString() {
        return nombre + "," + precio + "," + cantidad + "," + fecha;
    }

    public static Venta desdeTexto(String linea) {
        String[] partes = linea.split(",");

        String nombre = partes[0];
        int precio = Integer.parseInt(partes[1]);
        int cantidad = Integer.parseInt(partes[2]);
        String fecha = partes[3];

        Venta v = new Venta(nombre, precio, cantidad);
        v.fecha = fecha;

        return v;
    }
}