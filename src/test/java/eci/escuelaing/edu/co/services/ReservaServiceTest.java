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
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
        Laboratorio lab1 = new Laboratorio("Lab1", "Laboratorio de Física", "Edificio A", 30);
        Laboratorio lab2 = new Laboratorio("Lab2", "Laboratorio de Química", "Edificio B", 25);

        Reserva reserva1 = new Reserva("1", "1", lab1, null, "Practica 1", 1);
        Reserva reserva2 = new Reserva("2", "2", lab2, null, "Practica 2", 5);

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
        Laboratorio lab1 = new Laboratorio("Lab1", "Laboratorio de Física", "Edificio A", 30);
        LocalDateTime fechaHora = LocalDateTime.of(2025, 3, 10, 14, 30);
        
        Reserva reserva = new Reserva("1", "1", lab1, fechaHora, "Practica 1", 1);
        when(reservaRepository.findByIdFechaHora(fechaHora)).thenReturn(Optional.of(reserva));

        // Act
        var result = reservaService.ObtainReservaById(fechaHora);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("1", result.get().getUsuarioId());
        verify(reservaRepository, times(1)).findByIdFechaHora(fechaHora);
    }


    @Test
    void shouldCreateReserva() {
        // Arrange
        Laboratorio lab1 = new Laboratorio("Lab1", "Laboratorio de Física", "Edificio A", 30);
        LocalDateTime fechaHora = LocalDateTime.of(2025, 3, 10, 14, 30);
        
        Reserva reserva = new Reserva("1", "1", lab1, fechaHora, "Practica 1", 1);
        when(reservaRepository.findByIdFechaHora(fechaHora)).thenReturn(Optional.empty());
        when(reservaRepository.save(reserva)).thenReturn(reserva);

        // Act
        var result = reservaService.CreateReserva(reserva);

        // Assert
        assertEquals("1", result.getId());
        verify(reservaRepository, times(1)).findByIdFechaHora(fechaHora);
        verify(reservaRepository, times(1)).save(reserva);
    }


    @Test
    void shouldUpdateReserva() {
        // Arrange
        Laboratorio lab1 = new Laboratorio("Lab1", "Laboratorio de Física", "Edificio A", 30);
        Laboratorio lab2 = new Laboratorio("Lab2", "Laboratorio de Química", "Edificio B", 25);
        LocalDateTime fechaHora = LocalDateTime.of(2025, 4, 20, 10, 0);
        
        Reserva reserva = new Reserva("1", "1", lab1, fechaHora, "Practica 1", 1);
        Reserva reservaActualizada = new Reserva("1", "2", lab2, fechaHora, "Practica 2", 5);

        when(reservaRepository.findByIdFechaHora(fechaHora)).thenReturn(Optional.of(reserva));
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reservaActualizada);

        // Act
        var result = reservaService.UpdateReserva(fechaHora, reservaActualizada);

        // Assert
        assertEquals("2", result.getUsuarioId());
        assertEquals(lab2, result.getLaboratorio());
        assertEquals("Practica 2", result.getProposito());
        assertEquals(5, result.getPrioridad());
        
        verify(reservaRepository, times(1)).findByIdFechaHora(fechaHora);
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }


    @Test
    void shouldThrowExceptionWhenReservaNotFound() {
        Laboratorio lab2 = new Laboratorio("Lab2", "Laboratorio de Química", "Edificio B", 25);
        LocalDateTime fechaHora = LocalDateTime.of(2025, 4, 20, 10, 0);
        
        Reserva reservaActualizada = new Reserva("1", "2", lab2, fechaHora, "Practica 2", 5);
        
        when(reservaRepository.findByIdFechaHora(fechaHora)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> reservaService.UpdateReserva(fechaHora, reservaActualizada));

        verify(reservaRepository, times(1)).findByIdFechaHora(fechaHora);
        verify(reservaRepository, never()).save(any(Reserva.class));
    }


    @Test
    void shouldDeleteReserva() {
        // Arrange
        LocalDateTime fechaHora = LocalDateTime.of(2025, 4, 20, 10, 0);
        
        Reserva reserva = new Reserva("1", "1", new Laboratorio("Lab1", "Laboratorio de Física", "Edificio A", 30), 
                                    fechaHora, "Practica 1", 1);
        
        when(reservaRepository.findByIdFechaHora(fechaHora)).thenReturn(Optional.of(reserva));

        // Act
        reservaService.DeleteReserva(fechaHora);

        // Assert
        verify(reservaRepository, times(1)).findByIdFechaHora(fechaHora);
        verify(reservaRepository, times(1)).delete(reserva);
    }


}
