import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Estudiante {
    private String nombre;
    private String carnet;
    private String correo;
    private String seccion;
    private String semestre;
    private String descripcion;
    
    public Estudiante() {
        this.nombre = "";
        this.carnet = "";
        this.correo = "";
        this.seccion = "";
        this.semestre = "";
        this.descripcion = "";
    }
    
    // Getters
    public String getNombre() { return nombre; }
    public String getCarnet() { return carnet; }
    public String getCorreo() { return correo; }
    public String getSeccion() { return seccion; }
    public String getSemestre() { return semestre; }
    public String getDescripcion() { return descripcion; }
    
    // Setters
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCarnet(String carnet) { this.carnet = carnet; }
    public void setCorreo(String correo) { this.correo = correo; }
    public void setSeccion(String seccion) { this.seccion = seccion; }
    public void setSemestre(String semestre) { this.semestre = semestre; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    // Cargar desde archivo
    public void cargarDesdeArchivo() {
        try (BufferedReader br = new BufferedReader(new FileReader("estudiante.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("\\|");
                if (partes.length == 2) {
                    switch (partes[0]) {
                        case "NOMBRE": nombre = partes[1]; break;
                        case "CARNET": carnet = partes[1]; break;
                        case "CORREO": correo = partes[1]; break;
                        case "SECCION": seccion = partes[1]; break;
                        case "SEMESTRE": semestre = partes[1]; break;
                        case "DESCRIPCION": descripcion = partes[1]; break;
                    }
                }
            }
        } catch (IOException e) {
            // Datos por defecto si no existe el archivo
            nombre = "Estudiante IPC1";
            carnet = "202600000";
            correo = "estudiante@usac.edu.gt";
            seccion = "A";
            semestre = "Primer Semestre 2026";
            descripcion = "GameZone Pro - Proyecto de Introducción a la Programación y Computación 1";
        }
    }
}