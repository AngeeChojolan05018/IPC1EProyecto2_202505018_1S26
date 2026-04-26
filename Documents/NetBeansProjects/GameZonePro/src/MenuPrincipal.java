import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;

public class MenuPrincipal extends JFrame {

    private CardLayout cardLayout;
    private JPanel contenedor;
    private JTextArea areaCarrito;
    private ListaCarrito carritoLista = new ListaCarrito();
    private JLabel lblTotal;
    private JTable tablaCarrito;
    private DefaultTableModel modeloCarrito;
    private java.util.Map<String, Integer> stockMap = new java.util.HashMap<>();
    private java.util.Map<String, JLabel> stockLabels = new java.util.HashMap<>();  
    private ListaVentas listaVentas = new ListaVentas();
    

    public MenuPrincipal() {
        setTitle("GameZone Pro");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        contenedor = new JPanel(cardLayout);
        

        // Crear paneles
        contenedor.add(menuPrincipal(), "menu");
        contenedor.add(PanelTienda(), "tienda");
        contenedor.add(crearPanel("Álbum de Cartas"), "album");
        contenedor.add(crearPanel("Eventos Especiales"), "eventos");
        contenedor.add(crearPanel("Recompensas Y tablero de lideres"), "recompensas");
        contenedor.add(panelReportes(), "reportes");
        contenedor.add(crearPanel("Datos del Estudiante"), "datos");

        add(contenedor);

        listaVentas.cargarDesdeArchivo();
        
        addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
            listaVentas.guardarEnArchivo();
        }
    });
        
        setVisible(true);
    }

    // Opciones
    private JPanel menuPrincipal() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1, 10, 10));

        panel.add(crearBoton("Tienda de Videojuegos", "tienda"));
        panel.add(crearBoton("Álbum de Cartas", "album"));
        panel.add(crearBoton("Eventos Especiales", "eventos"));
        panel.add(crearBoton("Recompensas y Tablero de lideres", "recompensas"));
        panel.add(crearBoton("Reportes", "reportes"));
        panel.add(crearBoton("Datos del Estudiante", "datos"));

        JButton salir = new JButton("Salir");
        salir.setBackground(new Color(70, 130, 180));
        salir.setForeground(Color.WHITE);
        salir.setFont(new Font("Arial", Font.BOLD, 16));
        salir.setFocusPainted(false);
        salir.setBorderPainted(false);

        salir.addActionListener(e -> System.exit(0));
        panel.add(salir);

        return panel;
    }

    // BOTONES 
    private JButton crearBoton(String texto, String destino) {
        JButton boton = new JButton(texto);

        boton.setBackground(new Color(70, 130, 180));
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);

        boton.addActionListener(e -> cardLayout.show(contenedor, destino));
        return boton;
    }

    //Panel
    private JPanel crearPanel(String titulo) {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel(titulo, JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));

        JButton regresar = new JButton("Regresar al menú");

        regresar.setBackground(new Color(70, 130, 180));
        regresar.setForeground(Color.WHITE);
        regresar.setFont(new Font("Arial", Font.BOLD, 16));
        regresar.setFocusPainted(false);
        regresar.setBorderPainted(false);

        regresar.addActionListener(e -> cardLayout.show(contenedor, "menu"));

        panel.add(label, BorderLayout.CENTER);
        panel.add(regresar, BorderLayout.SOUTH);

    return panel;
}
    
    private JPanel PanelTienda() {
    JPanel panel = new JPanel(new BorderLayout());

    // Catalogo I
    JPanel catalogo = new JPanel();
    catalogo.setLayout(new GridLayout(0, 2, 10, 10));
    catalogo.setBackground(Color.LIGHT_GRAY);
    
    catalogo.add(crearTarjeta("J001", "GTA V", "Acción", "Q250", "PC", 10, "Juego de mundo abierto"));
    catalogo.add(crearTarjeta("J002", "FIFA 24", "Deportes", "Q300", "PlayStation", 5, "Simulador de fútbol"));
    catalogo.add(crearTarjeta("J003", "Zelda", "Aventura", "Q400", "Switch", 8, "Juego de aventura"));
    catalogo.add(crearTarjeta("J004", "Grand Theft Auto V", "Acción-aventura", "Q400", "Xbox 360", 3, "Narra la historia de tres criminales"));
    
    JScrollPane scrollCatalogo = new JScrollPane(catalogo);
    
    // Carrito PD
     JPanel carrito = new JPanel(new BorderLayout());
    carrito.setPreferredSize(new Dimension(250, 0));

    JLabel tituloCarrito = new JLabel("Carrito", JLabel.CENTER);

    JButton btnEliminar = new JButton("Eliminar seleccionado");

    btnEliminar.addActionListener(e -> {
    int fila = tablaCarrito.getSelectedRow();

    if (fila != -1) {
        String nombre = modeloCarrito.getValueAt(fila, 0).toString();
        int cantidad = Integer.parseInt(modeloCarrito.getValueAt(fila, 2).toString());

        // DEVOLVER STOCK
        int stockActual = stockMap.get(nombre);
        stockActual += cantidad;
        stockMap.put(nombre, stockActual);

        JLabel lbl = stockLabels.get(nombre);
        lbl.setText("Stock: " + stockActual);

        modeloCarrito.removeRow(fila);
        carritoLista.eliminar(nombre);

        actualizarTotal();
    }
});

    JPanel panelBotones = new JPanel();
    panelBotones.add(btnEliminar);

    JPanel panelTop = new JPanel(new BorderLayout());
    panelTop.add(tituloCarrito, BorderLayout.CENTER);
    panelTop.add(panelBotones, BorderLayout.SOUTH);

    carrito.add(panelTop, BorderLayout.NORTH);

    String[] columnas = {"Nombre", "Precio", "Cantidad"};

    modeloCarrito = new DefaultTableModel(columnas, 0) {
    public boolean isCellEditable(int row, int column) {
        return column == 2; // solo cantidad editable
    }
};

    tablaCarrito = new JTable(modeloCarrito);

    carrito.add(new JScrollPane(tablaCarrito), BorderLayout.CENTER);


    //Boton compra
    JButton btnComprar = new JButton("Confirmar compra");

    btnComprar.setBackground(new Color(34, 139, 34)); // verde
    btnComprar.setForeground(Color.WHITE);
    btnComprar.setFont(new Font("Arial", Font.BOLD, 14));
    btnComprar.setFocusPainted(false);
    btnComprar.setBorderPainted(false);
    
        btnComprar.addActionListener(e -> {

        if (modeloCarrito.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "El carrito está vacío");
            return;
        }

        StringBuilder sinStock = new StringBuilder();

        // VALIDAR STOCK
        for (int i = 0; i < modeloCarrito.getRowCount(); i++) {
            String nombre = modeloCarrito.getValueAt(i, 0).toString();
            int cantidad = Integer.parseInt(modeloCarrito.getValueAt(i, 2).toString());

            int stockActual = stockMap.get(nombre);

            if (cantidad > stockActual) {
                sinStock.append(nombre).append("\n");
            }
        }

        // SI HAY ERROR
        if (sinStock.length() > 0) {
            JOptionPane.showMessageDialog(null,
                    "Stock insuficiente para:\n" + sinStock.toString(),
                    "Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // PROCESAR COMPRA
        for (int i = 0; i < modeloCarrito.getRowCount(); i++) {
            String nombre = modeloCarrito.getValueAt(i, 0).toString();
            String precioTexto = modeloCarrito.getValueAt(i, 1).toString();
            int cantidad = Integer.parseInt(modeloCarrito.getValueAt(i, 2).toString());

            // quitar la Q y convertir a número
            int precio = Integer.parseInt(precioTexto.replace("Q", ""));
            
            listaVentas.agregar(new Venta(nombre, precio, cantidad));
            
            int stockActual = stockMap.get(nombre);
            stockActual -= cantidad;
            stockMap.put(nombre, stockActual);

            JLabel lbl = stockLabels.get(nombre);
            lbl.setText("Stock: " + stockActual);
        }

        JOptionPane.showMessageDialog(null, "Compra realizada con éxito");

        // LIMPIAR CARRITO
        modeloCarrito.setRowCount(0);
        carritoLista.vaciar();

        actualizarTotal();
    });
    
    // BOTÓN REGRESAR
    JButton regresar = new JButton("Regresar al menú");

    regresar.setBackground(new Color(70, 130, 180));
    regresar.setForeground(Color.WHITE);
    regresar.setFont(new Font("Arial", Font.BOLD, 16));
    regresar.setFocusPainted(false);
    regresar.setBorderPainted(false);

    regresar.addActionListener(e -> cardLayout.show(contenedor, "menu"));

    JPanel panelSur = new JPanel();
    panelSur.add(regresar);
    panelSur.add(btnComprar);
    

    // Títulos
    panel.add(scrollCatalogo, BorderLayout.CENTER);
    panel.add(carrito, BorderLayout.EAST);
    panel.add(panelSur, BorderLayout.SOUTH);
    
    // Total carrito
    lblTotal = new JLabel("Total: Q0");
    lblTotal.setHorizontalAlignment(JLabel.CENTER);

    carrito.add(lblTotal, BorderLayout.SOUTH);

    return panel;
}
    
    private JPanel crearTarjeta(String codigo, String nombre, String genero,String precio, String plataforma, int stock, String descripcion) {

    JPanel tarjeta = new JPanel();
    tarjeta.setLayout(new GridLayout(8, 1));
    tarjeta.setPreferredSize(new Dimension(200, 180));
    tarjeta.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

    JLabel lblCodigo = new JLabel("Código: " + codigo);
    JLabel lblNombre = new JLabel("Nombre: " + nombre);
    JLabel lblGenero = new JLabel("Género: " + genero);
    JLabel lblPrecio = new JLabel("Precio: " + precio);
    JLabel lblPlataforma = new JLabel("Plataforma: " + plataforma);

    final int[] stockNum = {stock}; 
    JLabel lblStock = new JLabel("Stock: " + stockNum[0]);
    stockMap.put(nombre, stockNum[0]);
    stockLabels.put(nombre, lblStock);

    JTextArea txtDescripcion = new JTextArea(descripcion);
    txtDescripcion.setLineWrap(true);
    txtDescripcion.setWrapStyleWord(true);
    txtDescripcion.setEditable(false);
    txtDescripcion.setBackground(null);

    JButton btnAgregar = new JButton("Agregar");

    btnAgregar.addActionListener(e -> {

    int stockActual = stockMap.get(nombre);

    if (stockActual> 0) {

        boolean encontrado = false;

        for (int i = 0; i < modeloCarrito.getRowCount(); i++) {
            String nombreTabla = modeloCarrito.getValueAt(i, 0).toString();

            if (nombreTabla.equals(nombre)) {
                int cantidad = Integer.parseInt(modeloCarrito.getValueAt(i, 2).toString());
                modeloCarrito.setValueAt(cantidad + 1, i, 2);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            modeloCarrito.addRow(new Object[]{nombre, precio, 1});
            carritoLista.agregar(nombre, precio);
        }

        actualizarTotal();

    } else {
        JOptionPane.showMessageDialog(null, "No hay stock disponible");
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
    
    private JPanel panelReportes() {
    JPanel panel = new JPanel(new BorderLayout());

    JLabel titulo = new JLabel("Historial de Ventas", JLabel.CENTER);
    titulo.setFont(new Font("Arial", Font.BOLD, 20));

    JTextArea areaVentas = new JTextArea();
    areaVentas.setEditable(false);

    JButton btnActualizar = new JButton("Actualizar");
    btnActualizar.addActionListener(e -> {
        areaVentas.setText(listaVentas.mostrarVentas());
    });

    JButton regresar = new JButton("Regresar al menú");
    regresar.addActionListener(e -> cardLayout.show(contenedor, "menu"));

    JPanel panelBotones = new JPanel();
    panelBotones.add(btnActualizar);
    panelBotones.add(regresar);

    panel.add(titulo, BorderLayout.NORTH);
    panel.add(new JScrollPane(areaVentas), BorderLayout.CENTER);
    panel.add(panelBotones, BorderLayout.SOUTH);

    return panel;
}
    
    private void actualizarTotal() {
    int total = 0;

    for (int i = 0; i < modeloCarrito.getRowCount(); i++) {
        String precioTexto = modeloCarrito.getValueAt(i, 1).toString();
        int cantidad = Integer.parseInt(modeloCarrito.getValueAt(i, 2).toString());

        int precio = Integer.parseInt(precioTexto.replace("Q", ""));

        total += precio * cantidad;
    }

    lblTotal.setText("Total: Q" + total);
}
    
    
    public static void main(String[] args) {
        new MenuPrincipal();
    }
}