import java.io.*;

public class ListaUsuarios {
    private NodoUsuario inicio;
    private int tamanio;
    
    public ListaUsuarios() {
        inicio = null;
        tamanio = 0;
    }
    
    public void agregar(String nombre, int xp) {
        NodoUsuario nuevo = new NodoUsuario(nombre, xp);
        
        if (inicio == null) {
            inicio = nuevo;
        } else {
            NodoUsuario actual = inicio;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
        tamanio++;
    }
    
    public void actualizarOAgregar(String nombre, int xp) {
        NodoUsuario actual = inicio;
        boolean encontrado = false;
        
        while (actual != null) {
            if (actual.nombre.equals(nombre)) {
                actual.xp = xp;
                encontrado = true;
                break;
            }
            actual = actual.siguiente;
        }
        
        if (!encontrado) {
            agregar(nombre, xp);
        }
    }
    
    // Bubble Sort manual (requerido por el PDF)
    public void ordenarPorXP() {
        if (inicio == null || inicio.siguiente == null) return;
        
        boolean intercambiado;
        do {
            intercambiado = false;
            NodoUsuario actual = inicio;
            
            while (actual != null && actual.siguiente != null) {
                if (actual.xp < actual.siguiente.xp) {
                    // Intercambiar datos
                    String tempNombre = actual.nombre;
                    int tempXP = actual.xp;
                    actual.nombre = actual.siguiente.nombre;
                    actual.xp = actual.siguiente.xp;
                    actual.siguiente.nombre = tempNombre;
                    actual.siguiente.xp = tempXP;
                    intercambiado = true;
                }
                actual = actual.siguiente;
            }
        } while (intercambiado);
    }
    
    public NodoUsuario getInicio() {
        return inicio;
    }
    
    public String[][] obtenerTop10() {
        ordenarPorXP();
        String[][] datos = new String[10][2];
        NodoUsuario actual = inicio;
        int i = 0;
        
        while (actual != null && i < 10) {
            datos[i][0] = actual.nombre;
            datos[i][1] = String.valueOf(actual.xp);
            actual = actual.siguiente;
            i++;
        }
        
        return datos;
    }
    
    public int getPosicionUsuario(String nombre) {
        ordenarPorXP();
        NodoUsuario actual = inicio;
        int posicion = 1;
        
        while (actual != null) {
            if (actual.nombre.equals(nombre)) {
                return posicion;
            }
            actual = actual.siguiente;
            posicion++;
        }
        return -1;
    }
    
    public void cargarDesdeArchivo() {
        try (BufferedReader br = new BufferedReader(new FileReader("leaderboard.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 2) {
                    agregar(partes[0], Integer.parseInt(partes[1]));
                }
            }
        } catch (IOException e) {
            System.out.println("No hay archivo de leaderboard, se creará uno nuevo");
        }
    }
    
    public void guardarEnArchivo() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("leaderboard.txt"))) {
            NodoUsuario actual = inicio;
            while (actual != null) {
                pw.println(actual.nombre + "," + actual.xp);
                actual = actual.siguiente;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
