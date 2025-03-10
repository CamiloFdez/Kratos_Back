package eci.escuelaing.edu.co.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class HorarioTest {

    private Horario horario;

    @BeforeEach
    public void setUp() {
        horario = new Horario("lab1", "Lunes", "08:30", "10:00", true);
    }

    @Test
    public void testConstructorAndGetters() {

        assertThat(horario.getLabId()).isEqualTo("lab1");
        assertThat(horario.getDia()).isEqualTo("Lunes");
        assertThat(horario.getHoraInicio()).isEqualTo("08:30");
        assertThat(horario.getHoraFin()).isEqualTo("10:00");
        assertThat(horario.isDisponible()).isTrue();
    }

    @Test
    public void testSetters() {

        horario.setId("horario1");
        horario.setLabId("lab2");
        horario.setDia("Martes");
        horario.setHoraInicio("10:00");
        horario.setHoraFin("11:30");
        horario.setDisponible(false);

        assertThat(horario.getId()).isEqualTo("horario1");
        assertThat(horario.getLabId()).isEqualTo("lab2");
        assertThat(horario.getDia()).isEqualTo("Martes");
        assertThat(horario.getHoraInicio()).isEqualTo("10:00");
        assertThat(horario.getHoraFin()).isEqualTo("11:30");
        assertThat(horario.isDisponible()).isFalse();
    }

    @Test
    public void testToString() {

        horario.setId("horario1");
        String expectedToString = "Horario{id='horario1', labId='lab1', dia='Lunes', horaInicio='08:30', horaFin='10:00', disponible=true}";
        assertThat(horario.toString()).isEqualTo(expectedToString);
    }
}
