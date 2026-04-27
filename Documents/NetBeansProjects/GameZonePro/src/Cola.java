public class Cola {

    private NodoCola frente;
    private NodoCola fin;
    private int tamanio;

    public Cola() {
        frente = null;
        fin = null;
        tamanio = 0;
    }

    public synchronized void encolar(String dato) {
        NodoCola nuevo = new NodoCola(dato);

        if (estaVacia()) {
            frente = nuevo;
            fin = nuevo;
        } else {
            fin.siguiente = nuevo;
            fin = nuevo;
        }

        tamanio++;
    }

    public synchronized String desencolar() {
        if (estaVacia()) return null;

        String dato = frente.dato;
        frente = frente.siguiente;

        if (frente == null) {
            fin = null;
        }

        tamanio--;
        return dato;
    }

    public boolean estaVacia() {
        return frente == null;
    }

    public int tamanio() {
        return tamanio;
    }

    public String peek() {
        return (frente != null) ? frente.dato : null;
    }

    public String mostrar() {
        String texto = "";
        NodoCola aux = frente;

        while (aux != null) {
            texto += aux.dato + "\n";
            aux = aux.siguiente;
        }

        
        return texto;
    }
}
