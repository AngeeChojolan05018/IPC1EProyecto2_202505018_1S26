import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.Desktop;

public class GeneradorReportes {
    
    // Reporte de Inventario - Usando ListaJuegos
    public static void generarReporteInventario(ListaJuegos catalogo, ListaVentas ventas) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss"));
        String nombreArchivo = timestamp + "_Inventario.html";
        
        try (PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo))) {
            pw.println("<!DOCTYPE html>");
            pw.println("<html>");
            pw.println("<head>");
            pw.println("<meta charset='UTF-8'>");
            pw.println("<title>Reporte de Inventario</title>");
            pw.println("<style>");
            pw.println("body { font-family: Arial, sans-serif; margin: 20px; background-color: #f0f0f0; }");
            pw.println("h1 { color: #2c3e50; text-align: center; }");
            pw.println("table { width: 80%; margin: 20px auto; border-collapse: collapse; background-color: white; }");
            pw.println("th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }");
            pw.println("th { background-color: #4CAF50; color: white; }");
            pw.println("tr:nth-child(even) { background-color: #f2f2f2; }");
            pw.println("</style>");
            pw.println("</head>");
            pw.println("<body>");
            pw.println("<h1>Reporte de Inventario - GameZone Pro</h1>");
            pw.println("<p>Fecha de generación: " + LocalDateTime.now() + "</p>");
            pw.println("<table border='1'>");
            pw.println("<tr><th>Código</th><th>Juego</th><th>Género</th><th>Precio</th><th>Plataforma</th><th>Stock</th></tr>");
            
            // Obtener todos los juegos de la lista enlazada
            Juego actual = catalogo.getInicio();
            while (actual != null) {
                pw.println("<tr>");
                pw.println("<td>" + actual.codigo + "</td>");
                pw.println("<td>" + actual.nombre + "</td>");
                pw.println("<td>" + actual.genero + "</td>");
                pw.println("<td>" + actual.precio + "</td>");
                pw.println("<td>" + actual.plataforma + "</td>");
                pw.println("<td>" + actual.stock + "</td>");
                pw.println("</tr>");
                actual = actual.siguiente;
            }
            
            pw.println("</table>");
            pw.println("</body>");
            pw.println("</html>");
            pw.close();
            
            File archivo = new File(nombreArchivo);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(archivo.toURI());
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Reporte de Ventas
    public static void generarReporteVentas(ListaVentas ventas) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss"));
        String nombreArchivo = timestamp + "_Ventas.html";
        
        try (PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo))) {
            pw.println("<!DOCTYPE html>");
            pw.println("<html>");
            pw.println("<head>");
            pw.println("<meta charset='UTF-8'>");
            pw.println("<title>Reporte de Ventas</title>");
            pw.println("<style>");
            pw.println("body { font-family: Arial, sans-serif; margin: 20px; background-color: #f0f0f0; }");
            pw.println("h1 { color: #2c3e50; text-align: center; }");
            pw.println("table { width: 80%; margin: 20px auto; border-collapse: collapse; background-color: white; }");
            pw.println("th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }");
            pw.println("th { background-color: #2196F3; color: white; }");
            pw.println("</style>");
            pw.println("</head>");
            pw.println("<body>");
            pw.println("<h1>Reporte de Ventas - GameZone Pro</h1>");
            pw.println("<p>Fecha de generación: " + LocalDateTime.now() + "</p>");
            pw.println("<table border='1'>");
            pw.println("<tr><th>Juego</th><th>Precio</th><th>Cantidad</th><th>Fecha</th></tr>");
            
            String ventasTexto = ventas.mostrarVentas();
            if (ventasTexto != null && !ventasTexto.isEmpty()) {
                // Parsear las ventas correctamente
                String[] lineas = ventasTexto.split("\n");
                for (String linea : lineas) {
                    if (!linea.trim().isEmpty()) {
                        // Intentar extraer datos de la línea
                        String juego = "";
                        String precio = "";
                        String cantidad = "";
                        String fecha = "";
                        
                        // Formato esperado: "Juego: XXX | Precio: QXXX | Cantidad: X | Fecha: XXX"
                        if (linea.contains("Juego:") && linea.contains("Precio:") && 
                            linea.contains("Cantidad:") && linea.contains("Fecha:")) {
                            juego = extraerValor(linea, "Juego:");
                            precio = extraerValor(linea, "Precio:");
                            cantidad = extraerValor(linea, "Cantidad:");
                            fecha = extraerValor(linea, "Fecha:");
                        }
                        
                        pw.println("<tr>");
                        pw.println("<td>" + juego + "</td>");
                        pw.println("<td>" + precio + "</td>");
                        pw.println("<td>" + cantidad + "</td>");
                        pw.println("<td>" + fecha + "</td>");
                        pw.println("</tr>");
                    }
                }
            } else {
                pw.println("<tr><td colspan='4'>No hay ventas registradas</td></tr>");
            }
            
            pw.println("</table>");
            pw.println("</body>");
            pw.println("</html>");
            pw.close();
            
            File archivo = new File(nombreArchivo);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(archivo.toURI());
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Método auxiliar para extraer valores
    private static String extraerValor(String linea, String clave) {
        int inicio = linea.indexOf(clave);
        if (inicio == -1) return "";
        inicio += clave.length();
        int fin = linea.indexOf("|", inicio);
        if (fin == -1) fin = linea.length();
        return linea.substring(inicio, fin).trim().replace("Q", "");
    }
    
    // Reporte del Álbum
    public static void generarReporteAlbum(MatrizAlbum album) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss"));
        String nombreArchivo = timestamp + "_Album.html";
        
        try (PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo))) {
            pw.println("<!DOCTYPE html>");
            pw.println("<html>");
            pw.println("<head>");
            pw.println("<meta charset='UTF-8'>");
            pw.println("<title>Reporte del Álbum</title>");
            pw.println("<style>");
            pw.println("body { font-family: Arial, sans-serif; margin: 20px; background-color: #f0f0f0; }");
            pw.println("h1 { color: #2c3e50; text-align: center; }");
            pw.println("table { border-collapse: collapse; margin: 20px auto; background-color: white; }");
            pw.println("td { border: 2px solid #333; padding: 10px; text-align: center; width: 140px; height: 120px; vertical-align: top; }");
            pw.println(".vacia { background-color: #cccccc; color: #666; }");
            pw.println(".legendaria { background-color: #FFD700; }");
            pw.println(".ultra-rara { background-color: #FF6347; }");
            pw.println(".rara { background-color: #87CEEB; }");
            pw.println(".comun { background-color: #90EE90; }");
            pw.println("</style>");
            pw.println("</head>");
            pw.println("<body>");
            pw.println("<h1>Reporte del Álbum de Cartas - GameZone Pro</h1>");
            pw.println("<p>Fecha de generación: " + LocalDateTime.now() + "</p>");
            pw.println("<p>Leyenda: Fuego | Agua | Planta | Eléctrico | Psíquico |Oscuro |️ Acero</p>");
            pw.println("<table>");
            
            NodoMatriz fila = album.getInicio();
            for (int i = 0; i < 4; i++) {
                pw.println("<tr>");
                NodoMatriz col = fila;
                for (int j = 0; j < 6; j++) {
                    if (col == null || col.dato == null) {
                        pw.println("<td class='vacia'>❌ Vacía<br>(" + i + "," + j + ")</td>");
                    } else {
                        String clase = "";
                        switch (col.dato.rareza) {
                            case "Legendaria": clase = "legendaria"; break;
                            case "Ultra Rara": clase = "ultra-rara"; break;
                            case "Rara": clase = "rara"; break;
                            default: clase = "comun";
                        }
                        pw.println("<td class='" + clase + "'>");
                        pw.println("<strong>" + col.dato.nombre + "</strong><br>");
                        pw.println(col.dato.tipo + "<br>");
                        pw.println( col.dato.rareza + "<br>");
                        pw.println(col.dato.ataque  + col.dato.defensa +  col.dato.ps);
                        pw.println("</td>");
                    }
                    col = (col != null) ? col.derecha : null;
                }
                pw.println("</tr>");
                fila = (fila != null) ? fila.abajo : null;
            }
            
            pw.println("</table>");
            pw.println("</body>");
            pw.println("</html>");
            pw.close();
            
            File archivo = new File(nombreArchivo);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(archivo.toURI());
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Reporte de Torneos
    public static void generarReporteTorneos(ListaTickets listaTickets) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss"));
        String nombreArchivo = timestamp + "_Torneos.html";
        
        try (PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo))) {
            pw.println("<!DOCTYPE html>");
            pw.println("<html>");
            pw.println("<head>");
            pw.println("<meta charset='UTF-8'>");
            pw.println("<title>Reporte de Torneos</title>");
            pw.println("<style>");
            pw.println("body { font-family: Arial, sans-serif; margin: 20px; background-color: #f0f0f0; }");
            pw.println("h1 { color: #2c3e50; text-align: center; }");
            pw.println("table { width: 80%; margin: 20px auto; border-collapse: collapse; background-color: white; }");
            pw.println("th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }");
            pw.println("th { background-color: #9C27B0; color: white; }");
            pw.println("tr:nth-child(even) { background-color: #f2f2f2; }");
            pw.println("</style>");
            pw.println("</head>");
            pw.println("<body>");
            pw.println("<h1>Reporte de Torneos - GameZone Pro</h1>");
            pw.println("<p>Fecha de generación: " + LocalDateTime.now() + "</p>");
            pw.println("<h2> Tickets Vendidos</h2>");
            pw.println("<table border='1'>");
            pw.println("<tr><th>#</th><th>Cliente</th><th>Fecha de Venta</th></tr>");
            
            String ticketsTexto = listaTickets.mostrar();
            int contador = 1;
            if (ticketsTexto != null && !ticketsTexto.isEmpty()) {
                String[] lineas = ticketsTexto.split("\n");
                for (String linea : lineas) {
                    if (!linea.trim().isEmpty()) {
                        pw.println("<tr>");
                        pw.println("<td>" + contador + "</td>");
                        pw.println("<td>" + linea + "</td>");
                        pw.println("<td>" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "</td>");
                        pw.println("</tr>");
                        contador++;
                    }
                }
            } else {
                pw.println("<tr><td colspan='3'>No hay tickets vendidos</td></tr>");
            }
            
            pw.println("</table>");
            pw.println("<p><strong>Total de tickets vendidos:</strong> " + (contador - 1) + "</p>");
            pw.println("</body>");
            pw.println("</html>");
            pw.close();
            
            File archivo = new File(nombreArchivo);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(archivo.toURI());
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
