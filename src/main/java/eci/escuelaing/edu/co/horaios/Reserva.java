package eci.escuelaing.edu.co.horaios;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reservas") // Para MongoDB
public class Reserva {
    @Id
    private String id;
    private String usuario;
    private String laboratorio;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    // Constructor vacío requerido por Spring
    public Reserva() {}

    public Reserva(String usuario, String laboratorio, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        this.usuario = usuario;
        this.laboratorio = laboratorio;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "id='" + id + '\'' +
                ", usuario='" + usuario + '\'' +
                ", laboratorio='" + laboratorio + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                '}';
    }
}
