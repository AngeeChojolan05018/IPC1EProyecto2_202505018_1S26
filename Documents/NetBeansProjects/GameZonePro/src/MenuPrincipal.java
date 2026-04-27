import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.File;

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
    private MatrizAlbum album = new MatrizAlbum(4, 6); 
    private String textoBusqueda = "";
    private JPanel panelAlbumRef;
    private JTextArea areaDetalle;
    private int filaSel = -1;
    private int colSel = -1;
    private Cola colaEventos = new Cola();
    private JTextArea areaCola;
    private JTextArea areaLog;
    private JLabel lblTaquilla1;
    private JLabel lblTaquilla2;
    private ListaTickets listaTickets = new ListaTickets();
    private JList<Torneo> listaTorneos;
    private Torneo torneoSeleccionado;
    private JLabel lblInfoTorneo;
    
    public MenuPrincipal() {
        setTitle("GameZone Pro");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        
        album.cargar();

        cardLayout = new CardLayout();
        contenedor = new JPanel(cardLayout);
        

        // Crear paneles
        contenedor.add(menuPrincipal(), "menu");
        contenedor.add(PanelTienda(), "tienda");
        panelAlbumRef = panelAlbum();
        contenedor.add(panelAlbumRef, "album");
        contenedor.add(crearPanel("Recompensas Y tablero de lideres"), "recompensas");
        contenedor.add(panelReportes(), "reportes");
        contenedor.add(crearPanel("Datos del Estudiante"), "datos");
        contenedor.add(panelEventos(), "eventos");

        add(contenedor);

        listaVentas.cargarDesdeArchivo();
        
        addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent e) {
            listaVentas.guardarEnArchivo();
        }
    });
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                listaTickets.guardarEnArchivo();
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
    
     private JPanel panelEventos() {

    JPanel panel = new JPanel(new BorderLayout());

    // TÍTULO
    JLabel titulo = new JLabel("Eventos Especiales - Venta de Tickets", JLabel.CENTER);
    titulo.setFont(new Font("Arial", Font.BOLD, 18));

    // INPUT USUARIO
    JPanel panelTop = new JPanel();

    JTextField txtNombre = new JTextField(15);
    JButton btnAgregar = new JButton("Entrar a la cola");

    panelTop.add(new JLabel("Nombre:"));
    panelTop.add(txtNombre);
    panelTop.add(btnAgregar);

    // CONTENEDOR SUPERIOR (TÍTULO + INPUT)
    JPanel topContainer = new JPanel(new BorderLayout());
    topContainer.add(titulo, BorderLayout.NORTH);
    topContainer.add(panelTop, BorderLayout.SOUTH);

    // LISTA DE TORNEOS
    DefaultListModel<Torneo> modeloTorneos = cargarTorneos();
    listaTorneos = new JList<>(modeloTorneos);

    JScrollPane scrollTorneos = new JScrollPane(listaTorneos);
    scrollTorneos.setBorder(BorderFactory.createTitledBorder("Torneos"));

    // INFO TORNEO
    lblInfoTorneo = new JLabel("Selecciona un torneo");

    listaTorneos.addListSelectionListener(e -> {
        torneoSeleccionado = listaTorneos.getSelectedValue();

        if (torneoSeleccionado != null) {
            lblInfoTorneo.setText(
                torneoSeleccionado.nombre + " | " +
                torneoSeleccionado.juego + " | Tickets: " +
                torneoSeleccionado.ticketsDisponibles
            );
        }
    });

    // AREA COLA
    areaCola = new JTextArea();
    areaCola.setEditable(false);
    areaCola.setBorder(BorderFactory.createTitledBorder("Cola de espera"));

    // AREA LOG
    areaLog = new JTextArea();
    areaLog.setEditable(false);
    areaLog.setBorder(BorderFactory.createTitledBorder("Log de ventas"));

    // CENTRO
    JPanel centro = new JPanel(new GridLayout(1, 2));
    centro.add(new JScrollPane(areaCola));
    centro.add(new JScrollPane(areaLog));

    // ESTADO TAQUILLAS
    JPanel panelTaquillas = new JPanel(new GridLayout(2,1));

    lblTaquilla1 = new JLabel("Taquilla 1: Libre");
    lblTaquilla2 = new JLabel("Taquilla 2: Libre");

    panelTaquillas.add(lblTaquilla1);
    panelTaquillas.add(lblTaquilla2);

    // BOTÓN INICIAR
    JButton btnIniciar = new JButton("Iniciar venta");

    btnIniciar.addActionListener(e -> {
        iniciarVenta();
    });

    // EVENTO AGREGAR A COLA
    btnAgregar.addActionListener(e -> {
        String nombre = txtNombre.getText();

        if (torneoSeleccionado == null) {
            JOptionPane.showMessageDialog(null, "Selecciona un torneo primero");
            return;
        }

        if (!nombre.isEmpty()) {
            colaEventos.encolar(nombre);
            txtNombre.setText("");
            actualizarCola();
        }
    });

    // BOTÓN REGRESAR
    JButton regresar = new JButton("Regresar");
    regresar.addActionListener(e -> cardLayout.show(contenedor, "menu"));

    // PANEL SUR
    JPanel sur = new JPanel();
    sur.setLayout(new GridLayout(2,1));

    JPanel botones = new JPanel();
    botones.add(btnIniciar);
    botones.add(regresar);

    sur.add(lblInfoTorneo);
    sur.add(botones);

    // AGREGAR TODO
    panel.add(topContainer, BorderLayout.NORTH);
    panel.add(scrollTorneos, BorderLayout.WEST);
    panel.add(centro, BorderLayout.CENTER);
    panel.add(panelTaquillas, BorderLayout.EAST);
    panel.add(sur, BorderLayout.SOUTH);

    return panel;
}
    
    private void actualizarCola() {
    areaCola.setText(colaEventos.mostrar());
}
    
    private void iniciarVenta() {
        if (torneoSeleccionado == null) {
            JOptionPane.showMessageDialog(null, "Selecciona un torneo primero");
            return;
        }

    Taquilla t1 = new Taquilla("Taquilla1", colaEventos, torneoSeleccionado, areaLog, lblTaquilla1, areaCola, listaTickets);
    Taquilla t2 = new Taquilla("Taquilla2", colaEventos, torneoSeleccionado, areaLog, lblTaquilla2, areaCola, listaTickets);

    t1.start();
    t2.start();
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
    
    
    private JPanel panelAlbum() {

    JPanel panel = new JPanel(new BorderLayout());

    JLabel titulo = new JLabel("Álbum de Cartas", JLabel.CENTER);
    titulo.setFont(new Font("Arial", Font.BOLD, 20));

    
    JTextField txtBuscar = new JTextField(15);

        txtBuscar.addKeyListener(new KeyAdapter() {
        public void keyReleased(KeyEvent e) {
            textoBusqueda = txtBuscar.getText();

            // refrescar panel
            contenedor.remove(panelAlbumRef);

            panelAlbumRef = panelAlbum();

            contenedor.add(panelAlbumRef, "album");

            cardLayout.show(contenedor, "album");
        }
    });
        
        
        JPanel top = new JPanel(new BorderLayout());
    top.add(titulo, BorderLayout.CENTER);
    top.add(txtBuscar, BorderLayout.SOUTH);

    areaDetalle = new JTextArea();
    areaDetalle.setEditable(false);
    areaDetalle.setPreferredSize(new Dimension(200, 0));
    areaDetalle.setBorder(BorderFactory.createTitledBorder("Detalles de la Carta"));
    
    
    JPanel grid = new JPanel(new GridLayout(4, 6, 5, 5));

    NodoMatriz fila = album.getInicio();

    for (int i = 0; i < 4; i++) {
        NodoMatriz col = fila;

        for (int j = 0; j < 6; j++) {

            JButton celda = new JButton();

            if (col.dato == null) {
                celda.setText("Vacío");
                celda.setBackground(Color.LIGHT_GRAY);
            } else {
                celda.setText("<html>" +
                        col.dato.nombre + "<br>" +
                        col.dato.tipo + "<br>" +
                        col.dato.rareza +
                        "</html>");

                celda.setBackground(obtenerColor(col.dato.tipo));
            }

            if (col.dato != null && textoBusqueda != null && !textoBusqueda.isEmpty() &&
                col.dato.nombre.toLowerCase().contains(textoBusqueda.toLowerCase())) {

                celda.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
            }

            int f = i;
            int c = j;

            celda.addActionListener(e -> {
                Carta carta = album.obtener(f, c);

        if (carta == null) {
            areaDetalle.setText("Espacio vacío");
        } else {
            areaDetalle.setText(
                "Código: " + carta.codigo +
                "\nNombre: " + carta.nombre +
                "\nTipo: " + carta.tipo +
                "\nRareza: " + carta.rareza +
                "\nAtaque: " + carta.ataque +
                "\nDefensa: " + carta.defensa +
                "\nPS: " + carta.ps
            );
            }
                if (filaSel == -1) {
            // Primera selección
            filaSel = f;
            colSel = c;

            areaDetalle.append("\n\nSeleccionada primera carta");

        } else {
            // Segunda selección → INTERCAMBIO

            Carta c1 = album.obtener(filaSel, colSel);
            Carta c2 = album.obtener(f, c);

            album.insertar(filaSel, colSel, c2);
            album.insertar(f, c, c1);

            // reset selección
            filaSel = -1;
            colSel = -1;

            refrescarAlbum();
        }

        });

            grid.add(celda);
            col = col.derecha;
        }

        fila = fila.abajo;
    }

    JScrollPane scroll = new JScrollPane(grid);

    // BOTONES
    JButton regresar = new JButton("Regresar");
    regresar.addActionListener(e -> cardLayout.show(contenedor, "menu"));

    JButton btnAgregar = new JButton("Agregar carta");

    btnAgregar.addActionListener(e -> {

        String[] nombres = {"Dragón", "Guerrero", "Mago", "Bestia", "Titán"};
        String[] tipos = {"Fuego", "Agua", "Planta", "Eléctrico", "Psíquico"};
        String[] rarezas = {"Común", "Rara", "Ultra Rara", "Legendaria"};

        int randNombre = (int)(Math.random() * nombres.length);
        int randTipo = (int)(Math.random() * tipos.length);
        int randRareza = (int)(Math.random() * rarezas.length);

        String codigo = "CARTA-" + (int)(Math.random() * 1000);

        int ataque = (int)(Math.random() * 100) + 50;
        int defensa = (int)(Math.random() * 100) + 50;
        int ps = (int)(Math.random() * 200) + 100;

        Carta nueva = new Carta(
            codigo,
            nombres[randNombre],
            tipos[randTipo],
            rarezas[randRareza],
            ataque,
            defensa,
            ps,
            ""
        );
        album.agregarPrimeraDisponible(nueva);

        refrescarAlbum();
    });

    JPanel panelBotones = new JPanel();
    panelBotones.add(btnAgregar);
    panelBotones.add(regresar);

    panel.add(top, BorderLayout.NORTH);
    panel.add(scroll, BorderLayout.CENTER);
    panel.add(areaDetalle, BorderLayout.EAST);
    panel.add(panelBotones, BorderLayout.SOUTH);

    return panel;
}
    
    
    private void agregarCarta(int fila, int columna) {

    String[] nombres = {"Dragón", "Guerrero", "Mago", "Bestia", "Titán"};
    String[] tipos = {"Fuego", "Agua", "Planta", "Eléctrico", "Psíquico"};
    String[] rarezas = {"Común", "Rara", "Ultra Rara", "Legendaria"};

    int randNombre = (int)(Math.random() * nombres.length);
    int randTipo = (int)(Math.random() * tipos.length);
    int randRareza = (int)(Math.random() * rarezas.length);

    String codigo = "CARTA-" + (int)(Math.random() * 1000);

    int ataque = (int)(Math.random() * 100) + 50;
    int defensa = (int)(Math.random() * 100) + 50;
    int ps = (int)(Math.random() * 200) + 100;

    Carta nueva = new Carta(
            codigo,
            nombres[randNombre],
            tipos[randTipo],
            rarezas[randRareza],
            ataque,
            defensa,
            ps,
            "ruta.png"
    );

    album.insertar(fila, columna, nueva);

    refrescarAlbum();
}
    
    private Color obtenerColor(String tipo) {
    switch (tipo) {
        case "Fuego": return Color.RED;
        case "Agua": return Color.CYAN;
        case "Planta": return Color.GREEN;
        case "Eléctrico": return Color.YELLOW;
        case "Psíquico": return Color.MAGENTA;
        case "Oscuro": return Color.DARK_GRAY;
        case "Acero": return Color.GRAY;
        default: return Color.WHITE;
        }
    }
    
    private void mostrarDetalleCarta(Carta carta) {
    JOptionPane.showMessageDialog(null,
            "Código: " + carta.codigo +
            "\nNombre: " + carta.nombre +
            "\nTipo: " + carta.tipo +
            "\nRareza: " + carta.rareza +
            "\nAtaque: " + carta.ataque +
            "\nDefensa: " + carta.defensa +
            "\nPS: " + carta.ps
        );
    }
    
        private void refrescarAlbum() {
    contenedor.remove(panelAlbumRef);

    panelAlbumRef = panelAlbum();

    contenedor.add(panelAlbumRef, "album");

    contenedor.revalidate();
    contenedor.repaint();

    cardLayout.show(contenedor, "album");
}
        
        private DefaultListModel<Torneo> cargarTorneos() {

    DefaultListModel<Torneo> modelo = new DefaultListModel<>();

    try {
        BufferedReader br = new BufferedReader(new FileReader("torneos.txt"));
        String linea;

        while ((linea = br.readLine()) != null) {

            String[] partes = linea.split("\\|");

            Torneo t = new Torneo(
                partes[0], // ID
                partes[1], // nombre
                partes[2], // juego
                partes[3], // fecha
                partes[4], // hora
                Integer.parseInt(partes[5]), // precio
                Integer.parseInt(partes[6])  // tickets
            );

            modelo.addElement(t);
        }

        br.close();

    } catch (Exception e) {
        e.printStackTrace();
    }

    return modelo;
}
        
    public static void main(String[] args) {
        new MenuPrincipal();
    }
}