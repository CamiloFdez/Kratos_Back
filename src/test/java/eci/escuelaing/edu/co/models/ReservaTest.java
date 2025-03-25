package eci.escuelaing.edu.co.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class ReservaTest {

    private Reserva reserva;

    @BeforeEach
    void setUp() {
        Laboratorio laboratorio = new Laboratorio("Lab A", "Laboratorio de Química", "Edificio B", 25);
        
        reserva = new Reserva("1", "user123", laboratorio, 
                LocalDateTime.of(2025, 3, 10, 14, 30), "Investigación", 2);
    }


    @Test
    void shouldCreateReservaCorrectly() {
        assertThat(reserva.getId()).isEqualTo("1");
        assertThat(reserva.getUsuarioId()).isEqualTo("user123");
        assertThat(reserva.getLaboratorio().getNombre()).isEqualTo("Laboratorio de Química"); 
        assertThat(reserva.getFechaHora()).isEqualTo(LocalDateTime.of(2025, 3, 10, 14, 30));
        assertThat(reserva.getProposito()).isEqualTo("Investigación");
        assertThat(reserva.getPrioridad()).isEqualTo(2);
    }



    @Test
void shouldModifyReservaCorrectly() {
    reserva.setId("2");
    reserva.setUsuarioId("user456");

    // Crear un nuevo objeto Laboratorio en lugar de pasar un String
    Laboratorio laboratorioB = new Laboratorio("LabB", "Laboratorio de Biología", "Edificio C", 20);
    reserva.setLaboratorio(laboratorioB);

    reserva.setFechaHora(LocalDateTime.of(2025, 5, 15, 10, 0));
    reserva.setProposito("Clase de MBDA");
    reserva.setPrioridad(4);

    // Verificaciones con AssertJ
    assertThat(reserva.getId()).isEqualTo("2");
    assertThat(reserva.getUsuarioId()).isEqualTo("user456");
    assertThat(reserva.getLaboratorio()).isEqualTo(laboratorioB); // Se compara el objeto completo
    assertThat(reserva.getFechaHora()).isEqualTo(LocalDateTime.of(2025, 5, 15, 10, 0));
    assertThat(reserva.getProposito()).isEqualTo("Clase de MBDA");
    assertThat(reserva.getPrioridad()).isEqualTo(4);
}


    @Test
    void shouldHandleNullValues() {
        reserva.setProposito(null);
        assertThat(reserva.getProposito()).isNull();
    }

    @Test
    void shouldHandleInvalidPriorities() {
        assertThatThrownBy(() -> reserva.setPrioridad(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("La prioridad debe estar entre 1 y 5.");

        assertThatThrownBy(() -> reserva.setPrioridad(6))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("La prioridad debe estar entre 1 y 5.");
    }
}
