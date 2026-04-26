
import java.io.*;

class ListaVentas {
    private NodoVenta cabeza;

    public void agregar(Venta venta) {
    NodoVenta nuevo = new NodoVenta(venta);
    nuevo.siguiente = cabeza;
    cabeza = nuevo;
}

    public void guardarEnArchivo() {
    try {
        BufferedWriter bw = new BufferedWriter(new FileWriter("historial.txt"));
        NodoVenta aux = cabeza;

        while (aux != null) {
            bw.write(aux.venta.toString());
            bw.newLine();
            aux = aux.siguiente;
        }

        bw.close();
    } catch (IOException e) {
        System.out.println("Error al guardar archivo");
    }
}
    
    public void cargarDesdeArchivo() {
    try {
        BufferedReader br = new BufferedReader(new FileReader("historial.txt"));
        String linea;

        while ((linea = br.readLine()) != null) {
            Venta v = Venta.desdeTexto(linea);
            agregar(v);
        }

        br.close();
    } catch (IOException e) {
        System.out.println("No hay archivo aún");
    }
}
    
    public String mostrarVentas() {
    String texto = "";
    NodoVenta actual = cabeza;

    while (actual != null) {
        texto += "Juego: " + actual.venta.nombre +
                 " | Precio: Q" + actual.venta.precio +
                 " | Cantidad: " + actual.venta.cantidad +
                 " | Fecha: " + actual.venta.fecha +
                 "\n";
        actual = actual.siguiente;
    }

    return texto;
}
    
    public String mostrar() {
        StringBuilder sb = new StringBuilder();
        NodoVenta aux = cabeza;

        while (aux != null) {
            sb.append(aux.venta.toString()).append("\n");
            aux = aux.siguiente;
        }

        return sb.toString();
    }
}