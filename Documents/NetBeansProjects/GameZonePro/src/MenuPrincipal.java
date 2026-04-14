import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuPrincipal extends JFrame {

    private CardLayout cardLayout;
    private JPanel contenedor;

    public MenuPrincipal() {
        setTitle("GameZone Pro");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        contenedor = new JPanel(cardLayout);

        // Crear paneles
        contenedor.add(menuPrincipal(), "menu");
        contenedor.add(crearPanel("Tienda de Videojuegos"), "tienda");
        contenedor.add(crearPanel("Álbum de Cartas"), "album");
        contenedor.add(crearPanel("Eventos Especiales"), "eventos");
        contenedor.add(crearPanel("Recompensas"), "recompensas");
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
        panel.add(crearBoton("Recompensas", "recompensas"));
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

    public static void main(String[] args) {
        new MenuPrincipal();
    }
}