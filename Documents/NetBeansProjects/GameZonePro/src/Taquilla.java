import javax.swing.*;

public class Taquilla extends Thread {

    private Cola cola;
    private Torneo torneo;
    private JTextArea log;
    private JLabel estado;
    private JTextArea areaCola;
    private ListaTickets lista;

    public Taquilla(String nombre, Cola cola, Torneo torneo, JTextArea log, JLabel estado, JTextArea areaCola, ListaTickets lista) {
        super(nombre);
        this.cola = cola;
        this.torneo = torneo;
        this.log = log;
        this.estado = estado;
        this.areaCola = areaCola;
        this.lista = lista;
    }

@Override
public void run() {

    while (true) {

        // Verificar tickets antes
        synchronized (torneo) {
            if (torneo.ticketsDisponibles <= 0) {
                SwingUtilities.invokeLater(() -> {
                    log.append("Tickets agotados\n");
                });
                break;
            }
        }

        String cliente = cola.desencolar();

        if (cliente == null) {
            break;
        }

        SwingUtilities.invokeLater(() -> {
            areaCola.setText(cola.mostrar());
            estado.setText(getName() + ": Procesando a " + cliente);
        });

        try {
            Thread.sleep((int)(Math.random() * 1200) + 800);
        } catch (InterruptedException e) {}

        boolean vendido = false;

        synchronized (torneo) {
            if (torneo.ticketsDisponibles > 0) {
                torneo.ticketsDisponibles--;
                vendido = true;
            }
        }

        if (vendido) {
            SwingUtilities.invokeLater(() -> {
                log.append(getName() + " vendió ticket a " + cliente + "\n");
                lista.agregar(cliente);
            });
        }
    }

    SwingUtilities.invokeLater(() -> {
        estado.setText(getName() + ": Finalizado");
    });
  }
}