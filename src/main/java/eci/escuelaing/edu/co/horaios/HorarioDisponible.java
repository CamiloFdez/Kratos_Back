package eci.escuelaing.edu.co.horaios;

import java.time.LocalDateTime;

public class HorarioDisponible {
    private String laboratorio;
    private LocalDateTime inicio;
    private LocalDateTime fin;
    private boolean reservado;

    public HorarioDisponible(String laboratorio, LocalDateTime inicio, LocalDateTime fin, boolean reservado) {
        this.laboratorio = laboratorio;
        this.inicio = inicio;
        this.fin = fin;
        this.reservado = reservado;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalDateTime inicio) {
        this.inicio = inicio;
    }

    public LocalDateTime getFin() {
        return fin;
    }

    public void setFin(LocalDateTime fin) {
        this.fin = fin;
    }

    public boolean isReservado() {
        return reservado;
    }

    public void setReservado(boolean reservado) {
        this.reservado = reservado;
    }

    @Override
    public String toString() {
        return "HorarioDisponible{" +
                "laboratorio='" + laboratorio + '\'' +
                ", inicio=" + inicio +
                ", fin=" + fin +
                ", reservado=" + reservado +
                '}';
    }
}
