public class ListaLogros {

    private NodoLogro inicio;

    public void agregar(String nombre, String descripcion) {
        NodoLogro nuevo = new NodoLogro(nombre, descripcion);

        if (inicio == null) {
            inicio = nuevo;
        } else {
            NodoLogro aux = inicio;
            while (aux.siguiente != null) {
                aux = aux.siguiente;
            }
            aux.siguiente = nuevo;
        }
    }

    public void desbloquear(String nombre) {
        NodoLogro aux = inicio;

        while (aux != null) {
            if (aux.nombre.equals(nombre) && !aux.desbloqueado) {
                aux.desbloqueado = true;

                javax.swing.JOptionPane.showMessageDialog(null,
                        "Logro desbloqueado: " + nombre);

                break;
            }
            aux = aux.siguiente;
        }
    }

    public String mostrar() {
        String texto = "";
        NodoLogro aux = inicio;

        while (aux != null) {
            if (aux.desbloqueado) {
                texto +=  aux.nombre + "\n";
            } else {
                texto += aux.nombre + "\n";
            }
            aux = aux.siguiente;
        }

        return texto;
    }
    public NodoLogro getInicio() {
    return inicio;
}
}