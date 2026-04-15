import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuPrincipal extends JFrame {

    private CardLayout cardLayout;
    private JPanel contenedor;
    private JTextArea areaCarrito;
    private ListaCarrito carritoLista = new ListaCarrito();
    private JLabel lblTotal;

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
        contenedor.add(crearPanel("Reportes"), "reportes");
        contenedor.add(crearPanel("Datos del Estudiante"), "datos");

        add(contenedor);

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
    
    catalogo.add(crearTarjeta("J001", "GTA V", "Acción", "Q250", "PC", "10", "Juego de mundo abierto"));
    catalogo.add(crearTarjeta("J002", "FIFA 24", "Deportes", "Q300", "PlayStation", "5", "Simulador de fútbol"));
    catalogo.add(crearTarjeta("J003", "Zelda", "Aventura", "Q400", "Switch", "8", "Juego de aventura"));
    catalogo.add(crearTarjeta("J004", "Grand Theft Auto V", "Acción-aventura", "Q400", "Xbox 360", "3", "Narra la historia de tres criminales"));
    
    JScrollPane scrollCatalogo = new JScrollPane(catalogo);
    
    // Carrito D
     JPanel carrito = new JPanel(new BorderLayout());
    carrito.setPreferredSize(new Dimension(250, 0));

    JLabel tituloCarrito = new JLabel("Carrito", JLabel.CENTER);
    carrito.add(tituloCarrito, BorderLayout.NORTH);

    areaCarrito = new JTextArea();
    carrito.add(new JScrollPane(areaCarrito), BorderLayout.CENTER);

    
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
    
    private JPanel crearTarjeta(String codigo, String nombre, String genero,String precio, String plataforma, String stock, String descripcion) {

    JPanel tarjeta = new JPanel();
    tarjeta.setLayout(new GridLayout(8, 1));
    tarjeta.setPreferredSize(new Dimension(200, 180));
    tarjeta.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

    JLabel lblCodigo = new JLabel("Código: " + codigo);
    JLabel lblNombre = new JLabel("Nombre: " + nombre);
    JLabel lblGenero = new JLabel("Género: " + genero);
    JLabel lblPrecio = new JLabel("Precio: " + precio);
    JLabel lblPlataforma = new JLabel("Plataforma: " + plataforma);
    final int[] stockNum = {Integer.parseInt(stock)};
    JLabel lblStock = new JLabel("Stock: " + stockNum[0]);

    JTextArea txtDescripcion = new JTextArea(descripcion);
    txtDescripcion.setLineWrap(true);
    txtDescripcion.setWrapStyleWord(true);
    txtDescripcion.setEditable(false);
    txtDescripcion.setBackground(null);

    JButton btnAgregar = new JButton("Agregar");

    btnAgregar.addActionListener(e -> {

    if (stockNum[0] > 0) {
        carritoLista.agregar(nombre, precio);
        areaCarrito.setText(carritoLista.mostrar());

        stockNum[0]--;
        lblStock.setText("Stock: " + stockNum[0]);
        
        // Total carrito
        int total = carritoLista.calcularTotal();
        lblTotal.setText("Total: Q" + total);

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
    public static void main(String[] args) {
        new MenuPrincipal();
    }
}