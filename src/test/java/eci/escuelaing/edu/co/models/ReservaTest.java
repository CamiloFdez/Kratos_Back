package eci.escuelaing.edu.co.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ReservaTest {

    private Reserva reserva;

    @BeforeEach
    void setUp() {
        reserva = new Reserva("1", "user123", "Lab A",
                LocalDateTime.of(2025, 3, 10, 14, 30), "Investigación");
    }

    @Test
    void shouldCreateReservaCorrectly() {
        assertThat(reserva.getId()).isEqualTo("1");
        assertThat(reserva.getUsuarioId()).isEqualTo("user123");
        assertThat(reserva.getLaboratorio()).isEqualTo("Lab A");
        assertThat(reserva.getFechaHora()).isEqualTo(LocalDateTime.of(2025, 3, 10, 14, 30));
        assertThat(reserva.getProposito()).isEqualTo("Investigación");
    }

    @Test
    void shouldModifyReservaCorrectly() {
        reserva.setId("2");
        reserva.setUsuarioId("user456");
        reserva.setLaboratorio("Lab B");
        reserva.setFechaHora(LocalDateTime.of(2025, 5, 15, 10, 0));
        reserva.setProposito("Clase de MBDA");

        assertThat(reserva.getId()).isEqualTo("2");
        assertThat(reserva.getUsuarioId()).isEqualTo("user456");
        assertThat(reserva.getLaboratorio()).isEqualTo("Lab B");
        assertThat(reserva.getFechaHora()).isEqualTo(LocalDateTime.of(2025, 5, 15, 10, 0));
        assertThat(reserva.getProposito()).isEqualTo("Clase de MBDA");
    }

    @Test
    void shouldHandleNullValues() {
        reserva.setProposito(null);
        assertThat(reserva.getProposito()).isNull();
    }
}
