package eci.escuelaing.edu.co.services;
import eci.escuelaing.edu.co.models.Reserva;
import eci.escuelaing.edu.co.repositories.ReservaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReservaServiceTest {
    @Mock
    private ReservaRepository reservaRepository;

    @InjectMocks
    private ReservaService reservaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllReservas() {
        // Arrange
        Reserva reserva1 = new Reserva("1", "1", "Lab1", null, "Practica 1", 1);
        Reserva reserva2 = new Reserva("2", "2", "Lab2", null, "Practica 2", 5);
        when(reservaRepository.findAll()).thenReturn(Arrays.asList(reserva1, reserva2));

        // Act
        var reservas = reservaService.ObtainAllReservas();

        // Assert
        assertEquals(2, reservas.size());
        verify(reservaRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnReservaById() {
        // Arrange
        Reserva reserva = new Reserva("1", "1", "Lab1", null, "Practica 1", 1);
        when(reservaRepository.findByFechaHora(null)).thenReturn(Optional.of(reserva));

        // Act
        var result = reservaService.ObtainReservaById(null);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("1", result.get().getUsuarioId());
        verify(reservaRepository, times(1)).findByFechaHora(null);
    }

    @Test
    void shouldCreateReserva() {
        // Arrange
        Reserva reserva = new Reserva("1", "1", "Lab1", null, "Practica 1", 1);
        when(reservaRepository.findByFechaHora(null)).thenReturn(Optional.empty());
        when(reservaRepository.save(reserva)).thenReturn(reserva);

        // Act
        var result = reservaService.CreateReserva(reserva);

        // Assert
        assertEquals("1", result.getId());
        verify(reservaRepository, times(1)).findByFechaHora(null);
        verify(reservaRepository, times(1)).save(reserva);
    }

    @Test
    void shouldUpdateReserva() {
        // Arrange
        Reserva reserva = new Reserva("1", "1", "Lab1", null, "Practica 1",1);
        Reserva reservaActualizada = new Reserva("1", "2", "Lab2", null, "Practica 2", 5);
        when(reservaRepository.findByFechaHora(null)).thenReturn(Optional.of(reserva));
        when(reservaRepository.save(reserva)).thenReturn(reserva);

        // Act
        var result = reservaService.UpdateReserva(null, reservaActualizada);

        // Assert
        assertEquals("2", result.getUsuarioId());
        assertEquals("Lab2", result.getLaboratorio());
        assertEquals("Practica 2", result.getProposito());
        verify(reservaRepository, times(1)).findByFechaHora(null);
        verify(reservaRepository, times(1)).save(reserva);
    }

    @Test
    void shouldThrowExceptionWhenReservaNotFound() {
        // Arrange
        Reserva reservaActualizada = new Reserva("1", "2", "Lab2", null, "Practica 2", 5);
        when(reservaRepository.findByFechaHora(null)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> reservaService.UpdateReserva(null, reservaActualizada));
        verify(reservaRepository, times(1)).findByFechaHora(null);
    }

    @Test
    void shouldDeleteReserva() {
        // Arrange
        Reserva reserva = new Reserva("1", "1", "Lab1", null, "Practica 1", 1);

        // Act
        reservaService.DeleteReserva(null);

        // Assert
        verify(reservaRepository, times(1)).deleteByFechaHora(null);
    }

    @Test
    void shouldDeleteReservaAndNotFindItAfter() {
       LocalDateTime fechaHora = LocalDateTime.now();
       Reserva reserva = new Reserva("1", "1", "Lab1", fechaHora, "Practica 1", 1);
       when(reservaRepository.findByFechaHora(fechaHora)).thenReturn(Optional.of(reserva)).thenReturn(Optional.empty());
       doNothing().when(reservaRepository).deleteByFechaHora(fechaHora);
       reservaService.DeleteReserva(fechaHora);
       var result = reservaService.ObtainReservaById(fechaHora);
       assertFalse(result.isPresent());
       verify(reservaRepository, times(1)).deleteByFechaHora(fechaHora);
       verify(reservaRepository, times(2)).findByFechaHora(fechaHora);
    }

    @Test
    void shouldReturnReservaWhenOneExists() {
        Reserva reserva = new Reserva("1", "1", "Lab1", LocalDateTime.now(), "Practica 1", 1);
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

}