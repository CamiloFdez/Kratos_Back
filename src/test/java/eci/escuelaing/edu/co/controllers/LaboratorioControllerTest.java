package eci.escuelaing.edu.co.controllers;

import eci.escuelaing.edu.co.models.Horario;
import eci.escuelaing.edu.co.models.Laboratorio;
import eci.escuelaing.edu.co.services.HorarioService;
import eci.escuelaing.edu.co.services.LaboratorioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LaboratorioControllerTest {

    @Mock
    private LaboratorioService laboratorioService;

    @Mock
    private HorarioService horarioService;

    @InjectMocks
    private LaboratorioController laboratorioController;

    private Laboratorio laboratorio;
    private Horario horario;

    @BeforeEach
    void setUp() {
        laboratorio = new Laboratorio("Lab1", "Laboratorio de Computación", "Edificio A, Piso 2", 30);
        horario = new Horario("Lab1", "Lunes", "08:30", "10:00", true);
    }


    @Test
    void obtenerLaboratorios_ShouldReturnList() {
        when(laboratorioService.getAllLaboratorios()).thenReturn(Arrays.asList(laboratorio));

        ResponseEntity<List<Laboratorio>> response = laboratorioController.obtenerLaboratorios();

        assertEquals(200, response.getStatusCode().value());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void obtenerLaboratorios_ShouldReturnNoContent() {
        when(laboratorioService.getAllLaboratorios()).thenReturn(List.of());

        ResponseEntity<List<Laboratorio>> response = laboratorioController.obtenerLaboratorios();

        assertEquals(204, response.getStatusCode().value());
    }

    @Test
    void obtenerHorariosDisponibles_ShouldReturnList() {
        when(horarioService.getHorariosByLabId("Lab1")).thenReturn(Arrays.asList(horario));

        ResponseEntity<List<Horario>> response = laboratorioController.obtenerHorariosDisponibles("Lab1");

        assertEquals(200, response.getStatusCode().value());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void obtenerHorariosDisponibles_ShouldReturnNoContent() {
        when(horarioService.getHorariosByLabId("Lab1")).thenReturn(List.of());

        ResponseEntity<List<Horario>> response = laboratorioController.obtenerHorariosDisponibles("Lab1");

        assertEquals(204, response.getStatusCode().value());
    }

    @Test
    void actualizarHorario_ShouldReturnUpdatedHorario() {
        when(horarioService.updateHorario(eq("Lab1"), any(Horario.class))).thenReturn(horario);

        ResponseEntity<Horario> response = laboratorioController.actualizarHorario("Lab1", horario);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void actualizarHorario_ShouldReturnNotFound() {
        when(horarioService.updateHorario(eq("Lab1"), any(Horario.class))).thenThrow(new RuntimeException("Not found"));

        ResponseEntity<Horario> response = laboratorioController.actualizarHorario("Lab1", horario);

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void eliminarHorario_ShouldReturnNoContent() {
        doNothing().when(horarioService).deleteHorario("1");

        ResponseEntity<Void> response = laboratorioController.eliminarHorario("Lab1", "1");

        assertEquals(204, response.getStatusCode().value());
    }

    @Test
    void eliminarHorario_ShouldReturnNotFound() {
        doThrow(new RuntimeException("Not found"))
                .when(horarioService).deleteHorario("1");

        ResponseEntity<Void> response = laboratorioController.eliminarHorario("Lab1", "1");

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void reservarLaboratorio_ShouldReturnReservedHorario() {
        when(horarioService.actualizarDisponibilidad(any(Horario.class), eq(false))).thenReturn(horario);

        ResponseEntity<Horario> response = laboratorioController.reservarLaboratorio("Lab1", horario);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void cancelarReserva_ShouldReturnCancelledHorario() {
        when(horarioService.actualizarDisponibilidad(any(Horario.class), eq(true))).thenReturn(horario);

        ResponseEntity<Horario> response = laboratorioController.cancelarReserva("Lab1", horario);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }
}
