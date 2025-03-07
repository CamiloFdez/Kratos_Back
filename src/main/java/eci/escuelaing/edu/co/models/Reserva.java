package eci.escuelaing.edu.co.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "reservas")
public class Reserva {

    @Id
    private String id;

    private String usuarioId;
    private String laboratorio; // Implementar clase 'laboratorio'
    private LocalDateTime fechaHora;
    private String proposito;

    public Reserva(String id, String usuarioId, String laboratorio, LocalDateTime fechaHora, String proposito) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.laboratorio = laboratorio;
        this.fechaHora = fechaHora;
        this.proposito = proposito;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }

    public String getLaboratorio() { return laboratorio; }
    public void setLaboratorio(String laboratorio) { this.laboratorio = laboratorio; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public String getProposito() { return proposito; }
    public void setProposito(String proposito) { this.proposito = proposito; }
}
