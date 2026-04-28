import java.io.*;

public class MatrizAlbum {
    private NodoMatriz inicio;
    private int filas;
    private int columnas;

    public MatrizAlbum(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        construir();
    }

private void construir() {
    NodoMatriz filaArriba = null;

    for (int i = 0; i < filas; i++) {

        NodoMatriz actual = null;
        NodoMatriz izquierda = null;
        NodoMatriz arriba = filaArriba;

        for (int j = 0; j < columnas; j++) {

            NodoMatriz nuevo = new NodoMatriz(null);

            if (i == 0 && j == 0) {
                inicio = nuevo;
            }

            // izquierda
            if (izquierda != null) {
                izquierda.derecha = nuevo;
                nuevo.izquierda = izquierda;
            }

            // arriba
            if (arriba != null) {
                arriba.abajo = nuevo;
                nuevo.arriba = arriba;
                arriba = arriba.derecha;
            }

            izquierda = nuevo;

            if (actual == null) {
                actual = nuevo;
            }
        }

        filaArriba = actual;
    }
}
    
    public void insertar(int fila, int columna, Carta carta) {
    NodoMatriz aux = inicio;

    for (int i = 0; i < fila; i++) {
        aux = aux.abajo;
    }

    for (int j = 0; j < columna; j++) {
        aux = aux.derecha;
    }

    aux.dato = carta;
}
    
    public Carta obtener(int fila, int columna) {
    NodoMatriz aux = inicio;

    for (int i = 0; i < fila; i++) {
        aux = aux.abajo;
    }

    for (int j = 0; j < columna; j++) {
        aux = aux.derecha;
    }

    return aux.dato;
}
    
    public void limpiar() {
    NodoMatriz fila = inicio;

    while (fila != null) {
        NodoMatriz col = fila;

        while (col != null) {
            col.dato = null;
            col = col.derecha;
        }

        fila = fila.abajo;
    }
}
    
    public void cargar() {
    limpiar();
    
    try {
        BufferedReader br = new BufferedReader(new FileReader("album.txt"));
        String linea;
        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split(",");
            Carta carta = new Carta(
                partes[0], partes[1], partes[2], partes[3], 
                Integer.parseInt(partes[4]), Integer.parseInt(partes[5]), 
                Integer.parseInt(partes[6]), partes.length > 7 ? partes[7] : ""
            );
            agregarPrimeraDisponible(carta);
        }
        br.close();
    } catch (IOException e) {
        System.out.println("No hay archivo o error al cargar");
    }

    
   limpiar();
    }
    
    public void agregarPrimeraDisponible(Carta carta) {

    NodoMatriz fila = inicio;

    while (fila != null) {
        NodoMatriz col = fila;

        while (col != null) {

            if (col.dato == null) {
                col.dato = carta;
                return; // ya la insertó, sale
            }

            col = col.derecha;
        }

        fila = fila.abajo;
    }

    System.out.println("Álbum lleno");
}
    
    public void intercambiar(int f1, int c1, int f2, int c2) {
    NodoMatriz n1 = obtenerNodo(f1, c1);
    NodoMatriz n2 = obtenerNodo(f2, c2);

    Carta temp = n1.dato;
    n1.dato = n2.dato;
    n2.dato = temp;
}
    
    private NodoMatriz obtenerNodo(int fila, int columna) {
    NodoMatriz aux = inicio;

    for (int i = 0; i < fila; i++) aux = aux.abajo;
    for (int j = 0; j < columna; j++) aux = aux.derecha;

    return aux;
}
    
    
    public void guardar() {
    try {
        BufferedWriter bw = new BufferedWriter(new FileWriter("album.txt"));

        NodoMatriz fila = inicio;

        while (fila != null) {
            NodoMatriz col = fila;

            while (col != null) {

                if (col.dato != null) {
                    bw.write(col.dato.toString());
                    bw.newLine();
                }

                col = col.derecha;
            }

            fila = fila.abajo;
        }

        bw.close();

    } catch (IOException e) {
        System.out.println("Error al guardar");
    }
}
    
    public NodoMatriz getInicio() {
    return inicio;
    }  
}