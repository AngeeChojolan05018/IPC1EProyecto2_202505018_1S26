
import java.io.*;

class ListaVentas {
    private NodoVenta cabeza;

    public void agregar(Venta venta) {
    NodoVenta nuevo = new NodoVenta(venta);
    nuevo.siguiente = inicio;
    inicio = nuevo;
}

    public void guardarEnArchivo() {
    try {
        BufferedWriter bw = new BufferedWriter(new FileWriter("historial.txt"));
        NodoVenta aux = inicio;

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
            // Suponiendo formato: nombre,precio,cantidad
            String[] datos = linea.split(",");

            String nombre = datos[0];
            int precio = Integer.parseInt(datos[1]);
            int cantidad = Integer.parseInt(datos[2]);

            agregar(new Venta(nombre, precio, cantidad));
        }

        br.close();
    } catch (IOException e) {
        System.out.println("No hay historial previo");
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