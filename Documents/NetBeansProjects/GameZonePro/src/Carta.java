public class Carta {
    String codigo;
    String nombre;
    String tipo;
    String rareza;
    int ataque;
    int defensa;
    int ps;
    String imagen;

    public Carta(String codigo, String nombre, String tipo, String rareza,
                 int ataque, int defensa, int ps, String imagen) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.tipo = tipo;
        this.rareza = rareza;
        this.ataque = ataque;
        this.defensa = defensa;
        this.ps = ps;
        this.imagen = imagen;
    }
    
        @Override
    public String toString() {
        return codigo + "," + nombre + "," + tipo + "," + rareza + "," +
               ataque + "," + defensa + "," + ps + "," + imagen;
    }
}
