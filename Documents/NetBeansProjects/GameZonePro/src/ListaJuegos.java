import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;

public class ListaJuegos {
    private Juego inicio;
    
    public ListaJuegos() {
        inicio = null;
    }
    
    public void agregar(Juego juego) {
        if (inicio == null) {
            inicio = juego;
        } else {
            Juego actual = inicio;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = juego;
        }
    }
    
    public Juego buscar(String nombre) {
        Juego actual = inicio;
        while (actual != null) {
            if (actual.nombre.equals(nombre)) {
                return actual;
            }
            actual = actual.siguiente;
        }
        return null;
    }
    
    public int getStock(String nombre) {
        Juego juego = buscar(nombre);
        return juego != null ? juego.stock : 0;
    }
    
    public void actualizarStock(String nombre, int nuevoStock) {
        Juego juego = buscar(nombre);
        if (juego != null) {
            juego.stock = nuevoStock;
        }
    }
    
    public Juego getInicio() {
        return inicio;
    }
    
    public void cargarDesdeArchivo() {
        try (BufferedReader br = new BufferedReader(new FileReader("catalogo.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("\\|");
                if (partes.length == 7) {
                    Juego juego = new Juego(
                        partes[0], partes[1], partes[2], partes[3],
                        partes[4], Integer.parseInt(partes[5]), partes[6]
                    );
                    agregar(juego);
                }
            }
        } catch (IOException e) {
            cargarPorDefecto();
        }
    }
    
    private void cargarPorDefecto() {
        agregar(new Juego("J001", "GTA V", "Acción", "Q250", "PC", 10, "Mundo abierto"));
        agregar(new Juego("J002", "FIFA 24", "Deportes", "Q300", "PlayStation", 5, "Fútbol"));
        agregar(new Juego("J003", "Zelda", "Aventura", "Q400", "Switch", 8, "Aventura"));
        agregar(new Juego("J004", "God of War", "Acción", "Q350", "PlayStation", 4, "Mitología"));
    }
    
    public void mostrarEnPanel(JPanel panelCatalogo, DefaultTableModel modeloCarrito, 
                                ListaCarrito carritoLista, Runnable actualizarTotal) {
        panelCatalogo.removeAll();
        panelCatalogo.setLayout(new GridLayout(0, 2, 10, 10));
        
        Juego actual = inicio;
        while (actual != null) {
            panelCatalogo.add(crearTarjeta(actual, modeloCarrito, carritoLista, actualizarTotal));
            actual = actual.siguiente;
        }
        
        panelCatalogo.revalidate();
        panelCatalogo.repaint();
    }
    
    private JPanel crearTarjeta(Juego juego, DefaultTableModel modeloCarrito, 
                                 ListaCarrito carritoLista, Runnable actualizarTotal) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new GridLayout(8, 1, 5, 5));
        tarjeta.setPreferredSize(new Dimension(220, 200));
        tarjeta.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        tarjeta.setBackground(Color.WHITE);
        
        JLabel lblCodigo = new JLabel("Código: " + juego.codigo);
        JLabel lblNombre = new JLabel("Nombre: " + juego.nombre);
        JLabel lblGenero = new JLabel("Género: " + juego.genero);
        JLabel lblPrecio = new JLabel("Precio: " + juego.precio);
        JLabel lblPlataforma = new JLabel("Plataforma: " + juego.plataforma);
        JLabel lblStock = new JLabel("Stock: " + juego.stock);
        
        // Cambiar color del stock según cantidad
        if (juego.stock == 0) {
            lblStock.setForeground(Color.RED);
        } else if (juego.stock < 5) {
            lblStock.setForeground(Color.ORANGE);
        } else {
            lblStock.setForeground(Color.GREEN);
        }
        
        JTextArea txtDescripcion = new JTextArea(juego.descripcion);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setEditable(false);
        txtDescripcion.setBackground(null);
        txtDescripcion.setFont(new Font("Arial", Font.PLAIN, 10));
        
        JButton btnAgregar = new JButton("🛒 Agregar");
        btnAgregar.setBackground(new Color(70, 130, 180));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setFocusPainted(false);
        
        btnAgregar.addActionListener(e -> {
            if (juego.stock > 0) {
                // Buscar si ya existe en el carrito
                boolean encontrado = false;
                for (int i = 0; i < modeloCarrito.getRowCount(); i++) {
                    String nombreTabla = modeloCarrito.getValueAt(i, 0).toString();
                    if (nombreTabla.equals(juego.nombre)) {
                        int cantidad = Integer.parseInt(modeloCarrito.getValueAt(i, 2).toString());
                        modeloCarrito.setValueAt(cantidad + 1, i, 2);
                        encontrado = true;
                        break;
                    }
                }
                if (!encontrado) {
                    modeloCarrito.addRow(new Object[]{juego.nombre, juego.precio, 1});
                    carritoLista.agregar(juego.nombre, juego.precio);
                }
                actualizarTotal.run();
            } else {
                JOptionPane.showMessageDialog(null, "⚠️ No hay stock disponible de " + juego.nombre, "Sin stock", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        tarjeta.add(lblCodigo);
        tarjeta.add(lblNombre);
        tarjeta.add(lblGenero);
        tarjeta.add(lblPrecio);
        tarjeta.add(lblPlataforma);
        tarjeta.add(lblStock);
        tarjeta.add(txtDescripcion);
        tarjeta.add(btnAgregar);
        
        return tarjeta;
    }
}