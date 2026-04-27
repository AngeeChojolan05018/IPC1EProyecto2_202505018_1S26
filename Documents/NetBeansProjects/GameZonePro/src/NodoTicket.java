public class NodoTicket {
    String cliente;
    NodoTicket siguiente;

    public NodoTicket(String cliente) {
        this.cliente = cliente;
        this.siguiente = null;
    }
}
