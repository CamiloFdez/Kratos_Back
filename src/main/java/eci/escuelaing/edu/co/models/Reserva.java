package eci.escuelaing.edu.co.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "reservas")
public class Reserva {

    @Id
    private String id;

    private String usuarioId;
    private Laboratorio laboratorio; 
    private LocalDateTime fechaHora;
    private String proposito;
    private int prioridad; 


    public Reserva() {}

    public Reserva(String id, String usuarioId, Laboratorio laboratorio, LocalDateTime fechaHora, String proposito, int prioridad) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.laboratorio = laboratorio;
        this.fechaHora = fechaHora;
        this.proposito = proposito;
        setPrioridad(prioridad);
    }

    // Getters y setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }

    public Laboratorio getLaboratorio() { return laboratorio; }
    public void setLaboratorio(Laboratorio laboratorio) { this.laboratorio = laboratorio; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public String getProposito() { return proposito; }
    public void setProposito(String proposito) { this.proposito = proposito; }

    public int getPrioridad() { return prioridad; }

    public void setPrioridad(int prioridad) {
        if (prioridad >= 1 && prioridad <= 5) {
            this.prioridad = prioridad;
        } else {
            throw new IllegalArgumentException("La prioridad debe estar entre 1 y 5.");
        }
    }
}
