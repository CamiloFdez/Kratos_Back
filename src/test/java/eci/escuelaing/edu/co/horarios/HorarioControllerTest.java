package eci.escuelaing.edu.co.horarios;

import eci.escuelaing.edu.co.horaios.HorarioController;
import eci.escuelaing.edu.co.horaios.ReservaRepository;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class HorarioControllerTest {

    @Mock
    private ReservaRepository reservaRepository;

    @InjectMocks
    private HorarioController horarioController;

    public HorarioControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

@Test
public void testEliminarHorario_SinReservasFuturas() {
    String laboratorio = "Lab1";
    LocalDateTime fecha = LocalDateTime.of(2024, 2, 27, 10, 0);

    // Simular que no hay reservas futuras en el horario
    when(reservaRepository.findByLaboratorioAndFechaInicioAfter(laboratorio, fecha))
            .thenReturn(Collections.emptyList());

    ResponseEntity<String> response = horarioController.eliminarHorario(laboratorio, fecha.toString());

    assertEquals("Horario eliminado correctamente.", response.getBody());
    verify(reservaRepository, times(1)).deleteByLaboratorioAndFechaInicio(laboratorio, fecha);
}

}
