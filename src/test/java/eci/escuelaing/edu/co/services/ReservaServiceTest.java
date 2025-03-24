package eci.escuelaing.edu.co.services;

import eci.escuelaing.edu.co.models.Laboratorio;
import eci.escuelaing.edu.co.models.Reserva;
import eci.escuelaing.edu.co.repositories.ReservaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReservaServiceTest {
    @Mock
    private ReservaRepository reservaRepository;

    @InjectMocks
    private ReservaService reservaService;

    private Laboratorio laboratorio1;
    private Laboratorio laboratorio2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        laboratorio1 = new Laboratorio("lab1", "Laboratorio de Física", "Edificio A", 30);
        laboratorio2 = new Laboratorio("lab2", "Laboratorio de Química", "Edificio B", 25);
    }

    @Test
    void shouldReturnAllReservas() {
        Reserva reserva1 = new Reserva("1", "1", laboratorio1, null, "Practica 1", 1);
        Reserva reserva2 = new Reserva("2", "2", laboratorio2, null, "Practica 2", 5);
        when(reservaRepository.findAll()).thenReturn(Arrays.asList(reserva1, reserva2));

        var reservas = reservaService.ObtainAllReservas();

        assertEquals(2, reservas.size());
        verify(reservaRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnReservaById() {

        Reserva reserva = new Reserva("1", "1", laboratorio1, null, "Practica 1", 1);
        when(reservaRepository.findByFechaHora(null)).thenReturn(Optional.of(reserva));

        var result = reservaService.ObtainReservaByFechaHora(null);

        assertTrue(result.isPresent());
        assertEquals("1", result.get().getUsuarioId());
        verify(reservaRepository, times(1)).findByFechaHora(null);
    }

    @Test
    void shouldCreateReserva() {
        Reserva reserva = new Reserva("1", "1", laboratorio1, null, "Practica 1", 1);
        when(reservaRepository.findByFechaHora(null)).thenReturn(Optional.empty());
        when(reservaRepository.save(reserva)).thenReturn(reserva);

        var result = reservaService.CreateReserva(reserva);

        assertEquals("1", result.getId());
        verify(reservaRepository, times(1)).findByFechaHora(null);
        verify(reservaRepository, times(1)).save(reserva);
    }

    @Test
    void shouldUpdateReserva() {
        Reserva reserva = new Reserva("1", "1", laboratorio1, null, "Practica 1",1);
        Reserva reservaActualizada = new Reserva("1", "2", laboratorio2, null, "Practica 2", 5);
        when(reservaRepository.findByFechaHora(null)).thenReturn(Optional.of(reserva));
        when(reservaRepository.save(reserva)).thenReturn(reserva);

        var result = reservaService.UpdateReserva(null, reservaActualizada);
        assertEquals("2", result.getUsuarioId());
        assertEquals(laboratorio2, result.getLaboratorio());
        assertEquals("Practica 2", result.getProposito());
        verify(reservaRepository, times(1)).findByFechaHora(null);
        verify(reservaRepository, times(1)).save(reserva);
    }

    @Test
    void shouldThrowExceptionWhenReservaNotFound() {
        // Arrange
        Reserva reservaActualizada = new Reserva("1", "2", laboratorio2, null, "Practica 2", 5);
        when(reservaRepository.findByFechaHora(null)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> reservaService.UpdateReserva(null, reservaActualizada));
        verify(reservaRepository, times(1)).findByFechaHora(null);
    }

    @Test
    void shouldDeleteReserva() {
        reservaService.DeleteReserva(null);
        verify(reservaRepository, times(1)).deleteByFechaHora(null);
    }

    @Test
    void shouldReturnReservaWhenOneExists() {
        Reserva reserva = new Reserva("1", "1", laboratorio1, LocalDateTime.now(), "Practica 1", 1);
        when(reservaRepository.findAll()).thenReturn(Arrays.asList(reserva));
        var reservas = reservaService.ObtainAllReservas();
        assertEquals(1, reservas.size());
        assertEquals("1", reservas.get(0).getId());
    }

    @Test
    void shouldReturnEmptyWhenNoReservaExists() {
        when(reservaRepository.findAll()).thenReturn(Arrays.asList());
        var reservas = reservaService.ObtainAllReservas();
        assertTrue(reservas.isEmpty());
    }

    @Test
    void shouldGenerateRandomReservations() {
        when(reservaRepository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));
        List<Reserva> reservas = reservaService.generarReservasAleatorias();
        assertNotNull(reservas, "La lista de reservas no debe ser nula");
        assertTrue(reservas.size() >= 100 && reservas.size() <= 1000,
                "La cantidad de reservas generadas debe estar entre 100 y 1000");
        for (Reserva reserva : reservas) {
            assertNotNull(reserva.getUsuarioId(), "El usuarioId no debe ser nulo");
            assertNotNull(reserva.getLaboratorio(), "El laboratorio no debe ser nulo");
            assertNotNull(reserva.getFechaHora(), "La fechaHora no debe ser nula");
            assertNotNull(reserva.getProposito(), "El propósito no debe ser nulo");
            assertTrue(reserva.getPrioridad() >= 1 && reserva.getPrioridad() <= 5,
                    "La prioridad debe estar entre 1 y 5");
        }
        verify(reservaRepository, times(1)).saveAll(anyList());
    }

    @Test
    void shouldReturnCorrectEstadisticasReservas() {
        // Arrange: Crear datos de prueba
        Reserva reserva1 = new Reserva("1", "1", laboratorio1, LocalDateTime.now().minusDays(1), "Practica 1", 3);
        Reserva reserva2 = new Reserva("2", "2", laboratorio1, LocalDateTime.now(), "Practica 2", 5);
        Reserva reserva3 = new Reserva("3", "3", laboratorio2, LocalDateTime.now(), "Practica 3", 2);
        Reserva reserva4 = new Reserva("4", "4", laboratorio2, LocalDateTime.now().minusDays(1), "Practica 4", 4);
        Reserva reserva5 = new Reserva("5", "5", laboratorio2, LocalDateTime.now().minusDays(2), "Practica 5", 1);
        when(reservaRepository.findAll()).thenReturn(Arrays.asList(reserva1, reserva2, reserva3, reserva4, reserva5));
        Map<String, Object> estadisticas = reservaService.obtenerEstadisticasReservas();
        assertNotNull(estadisticas);
        assertTrue(estadisticas.containsKey("histogramaReservas"));
        assertTrue(estadisticas.containsKey("reservasPorLaboratorio"));
        assertTrue(estadisticas.containsKey("promedioPrioridad"));
        assertTrue(estadisticas.containsKey("laboratoriosMasDemandados"));
        Map<String, Long> histogramaReservas = (Map<String, Long>) estadisticas.get("histogramaReservas");
        assertEquals(3, histogramaReservas.size());
        Map<String, Long> reservasPorLaboratorio = (Map<String, Long>) estadisticas.get("reservasPorLaboratorio");
        assertEquals(2, reservasPorLaboratorio.size());
        assertEquals(2, reservasPorLaboratorio.get(laboratorio1.getNombre()));
        assertEquals(3, reservasPorLaboratorio.get(laboratorio2.getNombre()));
        double promedioPrioridad = (double) estadisticas.get("promedioPrioridad");
        assertEquals(3.0, promedioPrioridad, 0.01);
        List<Map<String, Object>> laboratoriosMasDemandados = (List<Map<String, Object>>) estadisticas.get("laboratoriosMasDemandados");
        assertEquals(2, laboratoriosMasDemandados.size());
        assertEquals(laboratorio2.getNombre(), laboratoriosMasDemandados.get(0).get("nombre"));
        assertEquals(3L, laboratoriosMasDemandados.get(0).get("cantidad"));
    }

}