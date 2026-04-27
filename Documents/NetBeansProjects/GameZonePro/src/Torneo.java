public class Torneo {
    String id, nombre, juego, fecha, hora;
    int precio;
    int ticketsDisponibles;

    public Torneo(String id, String nombre, String juego,
                  String fecha, String hora, int precio, int tickets) {
        this.id = id;
        this.nombre = nombre;
        this.juego = juego;
        this.fecha = fecha;
        this.hora = hora;
        this.precio = precio;
        this.ticketsDisponibles = tickets;
    }
    
    @Override
public String toString() {
    return nombre + " - " + juego + " (" + ticketsDisponibles + " tickets)";
}
}