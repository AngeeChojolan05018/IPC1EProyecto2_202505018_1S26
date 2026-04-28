import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.Border;

// Clase principal
public class MenuPrincipal2 extends JFrame {
    private CardLayout cardLayout;
    private JPanel contenedor;
    private JTextArea areaCarrito;
    private ListaCarrito carritoLista = new ListaCarrito();
    private JLabel lblTotal;
    private JTable tablaCarrito;
    private DefaultTableModel modeloCarrito;
    private ListaVentas listaVentas = new ListaVentas();
    private MatrizAlbum album = new MatrizAlbum(4, 6);
    private String textoBusqueda = "";
    private JPanel panelAlbumRef;
    private JTextArea areaDetalle;
    private ListaJuegos catalogoJuegos = new ListaJuegos();
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
    private Usuario usuarioActual = new Usuario("Jugador1");
    private boolean[] filaPremiada = new boolean[4];
    private JLabel lblNivel;
    private JProgressBar barraXP;
    ListaLogros listaLogros = new ListaLogros();
    private int xp = 0;
    private JTextArea areaLogros;
    private int cartasObtenidas = 0;
    private int torneosJugados = 0;
    private int totalCompras = 0;
    private int totalGastado = 0;
    int cartasAgregadas = 0;
    int torneosComprados = 0;
    int dineroGastado = 0;
    
    public MenuPrincipal2() {
        setTitle("GameZone Pro");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        catalogoJuegos.cargarDesdeArchivo();
        
        areaLogros = new JTextArea();
        areaLogros.setEditable(false);
        areaLogros.setText(listaLogros.mostrar());
        usuarioActual.agregarXP(10);
        album.cargar();
        
        listaLogros.agregar("Primera Compra", "Realiza tu primera compra");
        listaLogros.agregar("Coleccionista Novato", "Añade 10 cartas");
        listaLogros.agregar("Coleccionista Experto", "Completa una fila del álbum");
        listaLogros.agregar("Taquillero", "Compra tickets para 3 torneos");
        listaLogros.agregar("Alta Rareza", "Obtén una carta legendaria");
        listaLogros.agregar("Gamer Dedicado", "Alcanza 1000 XP");
        listaLogros.agregar("Leyenda Viviente", "Alcanza el nivel 5");
        listaLogros.agregar("Gran Gastador", "Gasta más de Q2000");
        
        cardLayout = new CardLayout();
        contenedor = new JPanel(cardLayout);
        
        // Crear paneles
        contenedor.add(menuPrincipal(), "menu");
        contenedor.add(PanelTienda(), "tienda");
        panelAlbumRef = panelAlbum();
        contenedor.add(panelAlbumRef, "album");
        contenedor.add(panelRecompensas(), "recompensas");
        contenedor.add(panelReportes(), "reportes");
        contenedor.add(crearPanelDatosEstudiante(), "datos");
        contenedor.add(panelEventos(), "eventos");
        
        add(contenedor);
        
        listaVentas.cargarDesdeArchivo();
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                listaVentas.guardarEnArchivo();
                album.guardar();
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
        panel.setLayout(new GridLayout(8, 1, 10, 10));
        panel.add(crearBoton("Tienda de Videojuegos", "tienda"));
        panel.add(crearBoton("Álbum de Cartas", "album"));
        panel.add(crearBoton("Eventos Especiales", "eventos"));
        panel.add(crearBoton("Recompensas y Tablero de lideres", "recompensas"));
        panel.add(crearBoton("Reportes", "reportes"));
        panel.add(crearBoton("Datos del Estudiante", "datos"));
        panel.add(new JScrollPane(areaLogros));
        
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
    
    //PanelEstuandi
private JPanel crearPanelDatosEstudiante() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(new Color(240, 248, 255));
    
    // Título
    JLabel titulo = new JLabel("Datos del Estudiante", JLabel.CENTER);
    titulo.setFont(new Font("Arial", Font.BOLD, 24));
    titulo.setForeground(new Color(25, 25, 112));
    titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
    
    // Cargar datos del estudiante
    Estudiante estudiante = new Estudiante();
    estudiante.cargarDesdeArchivo();
    
    // Panel de información con borde
    JPanel infoPanel = new JPanel(new GridBagLayout());
    infoPanel.setBackground(Color.WHITE);
    infoPanel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
        BorderFactory.createEmptyBorder(20, 30, 20, 30)
    ));
    
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.WEST;
    gbc.insets = new Insets(10, 10, 10, 10);
    
    // Iconos y datos
    agregarCampo(infoPanel, gbc, "", "Nombre completo:", estudiante.getNombre(), 0);
    agregarCampo(infoPanel, gbc, "", "Número de carné:", estudiante.getCarnet(), 1);
    agregarCampo(infoPanel, gbc, "", "Correo electrónico:", estudiante.getCorreo(), 2);
    agregarCampo(infoPanel, gbc, "", "Sección:", estudiante.getSeccion(), 3);
    agregarCampo(infoPanel, gbc, "", "Semestre y año:", estudiante.getSemestre(), 4);
    
    // Descripción (ocupa toda la fila)
    gbc.gridx = 0;
    gbc.gridy = 5;
    gbc.gridwidth = 2;
    JLabel lblDescTitulo = new JLabel("📖 Acerca de GameZone Pro:");
    lblDescTitulo.setFont(new Font("Arial", Font.BOLD, 14));
    lblDescTitulo.setForeground(new Color(70, 130, 180));
    infoPanel.add(lblDescTitulo, gbc);
    
    gbc.gridy = 6;
    JTextArea txtDescripcion = new JTextArea(estudiante.getDescripcion());
    txtDescripcion.setLineWrap(true);
    txtDescripcion.setWrapStyleWord(true);
    txtDescripcion.setEditable(false);
    txtDescripcion.setBackground(infoPanel.getBackground());
    txtDescripcion.setFont(new Font("Arial", Font.PLAIN, 12));
    txtDescripcion.setRows(4);
    JScrollPane scrollDesc = new JScrollPane(txtDescripcion);
    scrollDesc.setBorder(BorderFactory.createEmptyBorder());
    infoPanel.add(scrollDesc, gbc);
    
    // Botón regresar
    JButton regresar = new JButton("← Regresar al menú principal");
    regresar.setBackground(new Color(70, 130, 180));
    regresar.setForeground(Color.WHITE);
    regresar.setFont(new Font("Arial", Font.BOLD, 16));
    regresar.setFocusPainted(false);
    regresar.setBorderPainted(false);
    regresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
    regresar.addActionListener(e -> cardLayout.show(contenedor, "menu"));
    
    JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
    panelBoton.setBackground(new Color(240, 248, 255));
    panelBoton.add(regresar);
    
    // Organizar panel
    JPanel centerPanel = new JPanel(new GridBagLayout());
    centerPanel.setBackground(new Color(240, 248, 255));
    centerPanel.add(infoPanel);
    
    panel.add(titulo, BorderLayout.NORTH);
    panel.add(centerPanel, BorderLayout.CENTER);
    panel.add(panelBoton, BorderLayout.SOUTH);
    
    return panel;
}

private void agregarCampo(JPanel panel, GridBagConstraints gbc, String icono, String label, String valor, int y) {
    gbc.gridx = 0;
    gbc.gridy = y;
    gbc.gridwidth = 1;
    JLabel lblIcono = new JLabel(icono);
    lblIcono.setFont(new Font("Segoe UI", Font.PLAIN, 20));
    panel.add(lblIcono, gbc);
    
    gbc.gridx = 1;
    JLabel lblLabel = new JLabel(label);
    lblLabel.setFont(new Font("Arial", Font.BOLD, 14));
    panel.add(lblLabel, gbc);
    
    gbc.gridx = 2;
    JLabel lblValor = new JLabel(valor);
    lblValor.setFont(new Font("Arial", Font.PLAIN, 14));
    lblValor.setForeground(new Color(70, 130, 180));
    panel.add(lblValor, gbc);
}
    
    private JPanel PanelTienda() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Catalogo
        JPanel catalogo = new JPanel();
        JScrollPane scrollCatalogo = new JScrollPane(catalogo);
        
        // Carrito
        JPanel carrito = new JPanel(new BorderLayout());
        carrito.setPreferredSize(new Dimension(300, 0));
        
        JLabel tituloCarrito = new JLabel("Carrito", JLabel.CENTER);
        JButton btnEliminar = new JButton("Eliminar seleccionado");
        btnEliminar.addActionListener(e -> {
            int fila = tablaCarrito.getSelectedRow();
            if (fila != -1) {
                String nombre = modeloCarrito.getValueAt(fila, 0).toString();
                int cantidad = Integer.parseInt(modeloCarrito.getValueAt(fila, 2).toString());
                // Devolver stock
                int stockActual = catalogoJuegos.getStock(nombre);
                catalogoJuegos.actualizarStock(nombre, stockActual + cantidad);
                modeloCarrito.removeRow(fila);
                carritoLista.eliminar(nombre);
                actualizarTotal();
                // Refrescar catálogo para actualizar labels de stock
                catalogoJuegos.mostrarEnPanel(catalogo, modeloCarrito, carritoLista, () -> actualizarTotal());
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
                return column == 2;
            }
        };
        tablaCarrito = new JTable(modeloCarrito);
        carrito.add(new JScrollPane(tablaCarrito), BorderLayout.CENTER);
        
        // Mostrar juegos en el catálogo
        catalogoJuegos.mostrarEnPanel(catalogo, modeloCarrito, carritoLista, () -> actualizarTotal());
        
        // Boton compra
        JButton btnComprar = new JButton("Confirmar compra");
        btnComprar.setBackground(new Color(34, 139, 34));
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
                int stockActual = catalogoJuegos.getStock(nombre);
                if (cantidad > stockActual) {
                    sinStock.append(nombre).append("\n");
                }
            }

            // SI HAY ERROR
            if (sinStock.length() > 0) {
                JOptionPane.showMessageDialog(null, "Stock insuficiente para:\n" + sinStock.toString(), "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // PROCESAR COMPRA
            for (int i = 0; i < modeloCarrito.getRowCount(); i++) {
                String nombre = modeloCarrito.getValueAt(i, 0).toString();
                String precioTexto = modeloCarrito.getValueAt(i, 1).toString();
                int cantidad = Integer.parseInt(modeloCarrito.getValueAt(i, 2).toString());
                int precio = Integer.parseInt(precioTexto.replace("Q", ""));
                listaVentas.agregar(new Venta(nombre, precio, cantidad));
                sumarXP(50 * cantidad);
                int stockActual = catalogoJuegos.getStock(nombre);
                catalogoJuegos.actualizarStock(nombre, stockActual - cantidad);
                totalCompras++;
                totalGastado += precio * cantidad;
            }

            JOptionPane.showMessageDialog(null, "Compra realizada con éxito");
            listaLogros.desbloquear("Primera Compra");
            verificarLogros();
            actualizarLogrosUI();

            // LIMPIAR CARRITO
            modeloCarrito.setRowCount(0);
            carritoLista.vaciar();
            actualizarTotal();
            
            // REFRESCAR CATÁLOGO
            catalogoJuegos.mostrarEnPanel(catalogo, modeloCarrito, carritoLista, () -> actualizarTotal());
        });
        
        // BOTÓN REGRESAR
        JButton regresar = new JButton("Regresar al menú");
        regresar.setBackground(new Color(70, 130, 180));
        regresar.setForeground(Color.WHITE);
        regresar.setFont(new Font("Arial", Font.BOLD, 16));
        regresar.setFocusPainted(false);
        regresar.setBorderPainted(false);
        regresar.addActionListener(e -> cardLayout.show(contenedor, "menu"));
        
        JPanel panelSur = new JPanel(new FlowLayout());
        panelSur.add(regresar);
        panelSur.add(btnComprar);
        
        panel.add(scrollCatalogo, BorderLayout.CENTER);
        panel.add(carrito, BorderLayout.EAST);
        panel.add(panelSur, BorderLayout.SOUTH);
        
        // Total carrito
        lblTotal = new JLabel("Total: Q0");
        lblTotal.setHorizontalAlignment(JLabel.CENTER);
        carrito.add(lblTotal, BorderLayout.SOUTH);
        
        return panel;
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
        
        JLabel titulo = new JLabel("Eventos Especiales - Venta de Tickets", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        
        JPanel panelTop = new JPanel();
        JTextField txtNombre = new JTextField(15);
        JButton btnAgregar = new JButton("Entrar a la cola");
        panelTop.add(new JLabel("Nombre:"));
        panelTop.add(txtNombre);
        panelTop.add(btnAgregar);
        
        DefaultListModel<Torneo> modeloTorneos = cargarTorneos();
        listaTorneos = new JList<>(modeloTorneos);
        JScrollPane scrollTorneos = new JScrollPane(listaTorneos);
        scrollTorneos.setBorder(BorderFactory.createTitledBorder("Torneos"));
        
        lblInfoTorneo = new JLabel("Selecciona un torneo");
        listaTorneos.addListSelectionListener(e -> {
            torneoSeleccionado = listaTorneos.getSelectedValue();
            if (torneoSeleccionado != null) {
                lblInfoTorneo.setText(torneoSeleccionado.nombre + " | " + torneoSeleccionado.juego + " | Tickets: " + torneoSeleccionado.ticketsDisponibles);
            }
        });
        
        areaCola = new JTextArea();
        areaCola.setEditable(false);
        areaCola.setBorder(BorderFactory.createTitledBorder("Cola de espera"));
        
        areaLog = new JTextArea();
        areaLog.setEditable(false);
        areaLog.setBorder(BorderFactory.createTitledBorder("Log de ventas"));
        
        JPanel centro = new JPanel(new GridLayout(1, 2));
        centro.add(new JScrollPane(areaCola));
        centro.add(new JScrollPane(areaLog));
        
        JPanel panelTaquillas = new JPanel(new GridLayout(2, 1));
        lblTaquilla1 = new JLabel("Taquilla 1: Libre");
        lblTaquilla2 = new JLabel("Taquilla 2: Libre");
        panelTaquillas.add(lblTaquilla1);
        panelTaquillas.add(lblTaquilla2);
        
        JButton btnIniciar = new JButton("Iniciar venta");
        btnIniciar.addActionListener(e -> iniciarVenta());
        
        btnAgregar.addActionListener(e -> {
            String nombre = txtNombre.getText();
            if (torneoSeleccionado == null) {
                JOptionPane.showMessageDialog(null, "Selecciona un torneo primero");
                return;
            }
            if (!nombre.isEmpty()) {
                colaEventos.encolar(nombre);
                torneosJugados++;
                actualizarLogrosUI();
                if (torneosJugados >= 3) {
                    listaLogros.desbloquear("Taquillero");
                }
                sumarXP(150);
                txtNombre.setText("");
                actualizarCola();
            }
        });
        
        JButton regresar = new JButton("Regresar");
        regresar.addActionListener(e -> cardLayout.show(contenedor, "menu"));
        
        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.add(titulo, BorderLayout.NORTH);
        topContainer.add(panelTop, BorderLayout.SOUTH);
        
        JPanel sur = new JPanel(new BorderLayout());
        sur.add(lblInfoTorneo, BorderLayout.CENTER);
        
        JPanel botones = new JPanel();
        botones.add(btnIniciar);
        botones.add(regresar);
        sur.add(botones, BorderLayout.SOUTH);
        
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
        areaDetalle.setPreferredSize(new Dimension(250, 0));
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
                    celda.setText("<html>" + col.dato.nombre + "<br>" + col.dato.tipo + "<br>" + col.dato.rareza + "</html>");
                    celda.setBackground(obtenerColor(col.dato.tipo));
                }
                if (col.dato != null && textoBusqueda != null && !textoBusqueda.isEmpty() && col.dato.nombre.toLowerCase().contains(textoBusqueda.toLowerCase())) {
                    celda.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
                }
                int f = i;
                int c = j;
                celda.addActionListener(e -> {
                    Carta carta = album.obtener(f, c);
                    if (carta == null) {
                        areaDetalle.setText("Espacio vacío");
                    } else {
                        areaDetalle.setText("Código: " + carta.codigo + "\nNombre: " + carta.nombre + "\nTipo: " + carta.tipo + "\nRareza: " + carta.rareza + "\nAtaque: " + carta.ataque + "\nDefensa: " + carta.defensa + "\nPS: " + carta.ps);
                    }
                    if (filaSel == -1) {
                        filaSel = f;
                        colSel = c;
                        areaDetalle.append("\n\nSeleccionada primera carta");
                    } else {
                        Carta c1 = album.obtener(filaSel, colSel);
                        Carta c2 = album.obtener(f, c);
                        album.insertar(filaSel, colSel, c2);
                        album.insertar(f, c, c1);
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
            Carta nueva = new Carta(codigo, nombres[randNombre], tipos[randTipo], rarezas[randRareza], ataque, defensa, ps, "");
            
            if (nueva.rareza.equals("Legendaria")) {
                listaLogros.desbloquear("Alta Rareza");
                sumarXP(200);
            }
            
            cartasObtenidas++;
            if (cartasObtenidas >= 10) {
                listaLogros.desbloquear("Coleccionista Novato");
            }
            
            album.agregarPrimeraDisponible(nueva);
            verificarFilaCompleta(0);
            verificarFilaCompleta(1);
            verificarFilaCompleta(2);
            verificarFilaCompleta(3);
            
            if (filaPremiada[0] || filaPremiada[1] || filaPremiada[2] || filaPremiada[3]) {
                listaLogros.desbloquear("Coleccionista Experto");
            }
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
    
    private JPanel panelRecompensas() {
        JPanel panel = new JPanel(new BorderLayout());
        
        JLabel titulo = new JLabel("Recompensas y Tablero de Líderes", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        
        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.setBorder(BorderFactory.createTitledBorder("Tu Progreso"));
        
        lblNivel = new JLabel("Nivel: 1 - Aprendiz", JLabel.CENTER);
        barraXP = new JProgressBar();
        barraXP.setStringPainted(true);
        barraXP.setPreferredSize(new Dimension(300, 25));
        
        JPanel panelNivel = new JPanel();
        panelNivel.setLayout(new BoxLayout(panelNivel, BoxLayout.Y_AXIS));
        lblNivel.setAlignmentX(Component.CENTER_ALIGNMENT);
        barraXP.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelNivel.add(lblNivel);
        panelNivel.add(Box.createRigidArea(new Dimension(0, 10)));
        panelNivel.add(barraXP);
        
        areaLogros = new JTextArea();
        areaLogros.setEditable(false);
        areaLogros.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollLogros = new JScrollPane(areaLogros);
        scrollLogros.setBorder(BorderFactory.createTitledBorder("Logros"));
        
        panelIzquierdo.add(panelNivel, BorderLayout.NORTH);
        panelIzquierdo.add(scrollLogros, BorderLayout.CENTER);
        
        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setBorder(BorderFactory.createTitledBorder("Tablero de Líderes"));
        
        String[] columnas = {"Posición", "Usuario", "XP"};
        DefaultTableModel modeloLeaderboard = new DefaultTableModel(columnas, 0);
        JTable tablaLeaderboard = new JTable(modeloLeaderboard);
        JScrollPane scrollLeaderboard = new JScrollPane(tablaLeaderboard);
        
        JButton btnActualizarLeaderboard = new JButton("Actualizar Tablero");
        btnActualizarLeaderboard.addActionListener(e -> actualizarLeaderboard(modeloLeaderboard));
        
        JPanel panelBotonesLeaderboard = new JPanel();
        panelBotonesLeaderboard.add(btnActualizarLeaderboard);
        
        panelDerecho.add(scrollLeaderboard, BorderLayout.CENTER);
        panelDerecho.add(panelBotonesLeaderboard, BorderLayout.SOUTH);
        
        JPanel panelCentro = new JPanel(new GridLayout(1, 3, 10, 10));
        panelCentro.setBorder(BorderFactory.createTitledBorder("Podio"));
        
        JPanel oro = crearPanelPodio(" 1er Lugar", Color.ORANGE);
        JPanel plata = crearPanelPodio("2do Lugar", Color.LIGHT_GRAY);
        JPanel bronce = crearPanelPodio("3er Lugar", new Color(205, 127, 50));
        
        panelCentro.add(oro);
        panelCentro.add(plata);
        panelCentro.add(bronce);
        
        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnReporteInventario = new JButton("Reporte Inventario");
        JButton btnReporteVentas = new JButton("Reporte Ventas");
        JButton btnReporteAlbum = new JButton("Reporte Álbum");
        JButton btnReporteTorneos = new JButton("Reporte Torneos");
        JButton regresar = new JButton("Regresar al menú");
        
          
        btnReporteInventario.addActionListener(e -> {
            GeneradorReportes.generarReporteInventario(catalogoJuegos, listaVentas);
        });

        btnReporteVentas.addActionListener(e -> {
            GeneradorReportes.generarReporteVentas(listaVentas);
        });

        btnReporteAlbum.addActionListener(e -> {
            GeneradorReportes.generarReporteAlbum(album);
        });

        btnReporteTorneos.addActionListener(e -> {
            GeneradorReportes.generarReporteTorneos(listaTickets);
        });
        
        regresar.addActionListener(e -> cardLayout.show(contenedor, "menu"));
        
        panelBotones.add(btnReporteInventario);
        panelBotones.add(btnReporteVentas);
        panelBotones.add(btnReporteAlbum);
        panelBotones.add(btnReporteTorneos);
        panelBotones.add(regresar);
        
        JPanel panelSuperior = new JPanel(new GridLayout(1, 2));
        panelSuperior.add(panelIzquierdo);
        panelSuperior.add(panelDerecho);
        
        panel.add(titulo, BorderLayout.NORTH);
        panel.add(panelSuperior, BorderLayout.CENTER);
        panel.add(panelCentro, BorderLayout.SOUTH);
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        actualizarNivelUI();
        actualizarLogrosUI();
        
        return panel;
    }
    
    private JPanel crearPanelPodio(String titulo, Color color) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(color);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        JLabel lblTitulo = new JLabel(titulo, JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        JLabel lblUsuario = new JLabel("---", JLabel.CENTER);
        
        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(lblUsuario, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void actualizarLeaderboard(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        modelo.addRow(new Object[]{1, usuarioActual.getNombre(), usuarioActual.getXP()});
        modelo.addRow(new Object[]{2, "---", "---"});
        modelo.addRow(new Object[]{3, "---", "---"});
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
                Torneo t = new Torneo(partes[0], partes[1], partes[2], partes[3], partes[4], Integer.parseInt(partes[5]), Integer.parseInt(partes[6]));
                modelo.addElement(t);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelo;
    }
    
    private void verificarFilaCompleta(int fila) {
        int contador = 0;
        for (int j = 0; j < 6; j++) {
            if (album.obtener(fila, j) != null) {
                contador++;
            }
        }
        if (contador == 6 && !filaPremiada[fila]) {
            filaPremiada[fila] = true;
            sumarXP(100);
            JOptionPane.showMessageDialog(null, "¡Fila completada! +100 XP");
        }
    }
    
    private void actualizarNivelUI() {
        lblNivel.setText("Nivel: " + usuarioActual.getNivel() + " - " + usuarioActual.getRango());
        barraXP.setMaximum(usuarioActual.getXPNecesario());
        barraXP.setValue(usuarioActual.getXPProgreso());
        barraXP.setString(usuarioActual.getXPProgreso() + "/" + usuarioActual.getXPNecesario() + " XP");
    }
    
    private void sumarXP(int cantidad) {
        xp += cantidad;
        usuarioActual.agregarXP(cantidad);
        verificarLogros();
        actualizarNivelUI();
    }
    
    private void verificarLogros() {
        if (totalCompras >= 1) {
            listaLogros.desbloquear("Primera Compra");
        }
        if (cartasObtenidas >= 10) {
            listaLogros.desbloquear("Coleccionista Novato");
        }
        if (filaPremiada[0] || filaPremiada[1] || filaPremiada[2] || filaPremiada[3]) {
            listaLogros.desbloquear("Coleccionista Experto");
        }
        if (torneosJugados >= 3) {
            listaLogros.desbloquear("Taquillero");
        }
        if (xp >= 1000) {
            listaLogros.desbloquear("Gamer Dedicado");
        }
        if (xp >= 7000) {
            listaLogros.desbloquear("Leyenda Viviente");
        }
        if (totalGastado >= 2000) {
            listaLogros.desbloquear("Gran Gastador");
        }
        actualizarLogrosUI();
    }
    
    private void actualizarLogrosUI() {
        String texto = "=== LOGROS ===\n\n";
        texto += "Primera Compra - Realiza tu primera compra\n";
        texto += "Coleccionista Novato - Añade 10 cartas\n";
        texto += "Coleccionista Experto - Completa una fila\n";
        texto += "Taquillero - Compra tickets para 3 torneos\n";
        texto += "Alta Rareza - Obtén carta Legendaria\n";
        texto += "Gamer Dedicado - Alcanza 1000 XP\n";
        texto += "Leyenda Viviente - Alcanza Nivel 5\n";
        texto += "Gran Gastador - Gasta más de Q2000\n";
        
        areaLogros.setText(texto);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPrincipal2());
    }
}
