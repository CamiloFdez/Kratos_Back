package eci.escuelaing.edu.co.models;

import org.springframework.data.annotation.Id;  // Para usar la anotación @Id en el campo de identificación
import org.springframework.data.mongodb.core.mapping.Document;  // Para mapear la clase a una colección de MongoDB

@Document(collection = "horarios")  // Define la colección en la que se almacenarán los horarios
public class Horario {

    @Id
    private String id;  // El ID del horario (autogenerado por MongoDB)

    private String labId;  // El ID del laboratorio al que pertenece este horario
    private String dia;  // El día de la semana (por ejemplo, Lunes, Martes, etc.)
    private String horaInicio;  // La hora de inicio del horario (por ejemplo, "08:00")
    private String horaFin;  // La hora de fin del horario (por ejemplo, "10:00")
    private boolean disponible;  // Indica si el horario está disponible para ser reservado (true/false)

    // Constructor vacío (requerido por MongoDB y Spring Data)
    public Horario() {}

    // Constructor con parámetros
    public Horario(String labId, String dia, String horaInicio, String horaFin, boolean disponible) {
        this.labId = labId;
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.disponible = disponible;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    // Método para mostrar la información del horario en formato legible
    @Override
    public String toString() {
        return "Horario{" +
                "id='" + id + '\'' +
                ", labId='" + labId + '\'' +
                ", dia='" + dia + '\'' +
                ", horaInicio='" + horaInicio + '\'' +
                ", horaFin='" + horaFin + '\'' +
                ", disponible=" + disponible +
                '}';
    }
}
