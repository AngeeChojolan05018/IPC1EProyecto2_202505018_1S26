import java.io.PrintWriter;

public class ListaTickets {

    private NodoTicket inicio;

    public void agregar(String cliente) {
        NodoTicket nuevo = new NodoTicket(cliente);

        if (inicio == null) {
            inicio = nuevo;
        } else {
            NodoTicket aux = inicio;

            while (aux.siguiente != null) {
                aux = aux.siguiente;
            }

            aux.siguiente = nuevo;
        }
    }

    public String mostrar() {
        String texto = "";
        NodoTicket aux = inicio;

        while (aux != null) {
            texto += aux.cliente + "\n";
            aux = aux.siguiente;
        }

        return texto;
    }

    // GUARDAR EN ARCHIVO
    public void guardarEnArchivo() {
        try {
            PrintWriter pw = new PrintWriter("tickets_vendidos.txt");

            NodoTicket aux = inicio;

            while (aux != null) {
                pw.println(aux.cliente);
                aux = aux.siguiente;
            }

            pw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}