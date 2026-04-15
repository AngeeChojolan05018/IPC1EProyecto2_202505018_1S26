public class ListaCarrito { //
    Nodo inicio;

    public ListaCarrito() {
        inicio = null;
    }

    public void agregar(String nombre, String precio) {
        Nodo nuevo = new Nodo(nombre, precio);

        if (inicio == null) {
            inicio = nuevo;
        } else {
            Nodo aux = inicio;
            while (aux.siguiente != null) {
                aux = aux.siguiente;
            }
            aux.siguiente = nuevo;
        }
    }

    public String mostrar() {
        String texto = "";
        Nodo aux = inicio;

        while (aux != null) {
            texto += aux.nombre + " - " + aux.precio + "\n";
            aux = aux.siguiente;
        }

        return texto;
    }
    
    public int calcularTotal() {
    int total = 0;
    Nodo aux = inicio;

    while (aux != null) {
        String precioTexto = aux.precio.replace("Q", "");
        int precio = Integer.parseInt(precioTexto);
        total += precio;

        aux = aux.siguiente;
    }

    return total;
    }
    
}
