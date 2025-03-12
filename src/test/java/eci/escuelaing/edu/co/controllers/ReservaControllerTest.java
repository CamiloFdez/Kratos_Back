package eci.escuelaing.edu.co.controllers;

import eci.escuelaing.edu.co.models.Reserva;
import eci.escuelaing.edu.co.services.ReservaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservaControllerTest {

    @Mock
    private ReservaService reservaService;

    @InjectMocks
    private ReservaController reservaController;

    private Reserva reserva;
    private LocalDateTime fechaHora;

    @BeforeEach
    void setUp() {
        fechaHora = LocalDateTime.of(2025, 3, 7, 14, 0);
        reserva = new Reserva("R123", "U456", "Lab1", fechaHora, "Programación Avanzada", 2);
    }


    @Test
    void testListReservas() {
        when(reservaService.ObtainAllReservas()).thenReturn(List.of(reserva));

        List<Reserva> reservas = reservaController.listReservas();

        assertNotNull(reservas);
        assertEquals(1, reservas.size());
        verify(reservaService, times(1)).ObtainAllReservas();
    }

    @Test
    void testObtainReservaById_Found() {
        when(reservaService.ObtainReservaById(fechaHora)).thenReturn(Optional.of(reserva));

        ResponseEntity<Reserva> response = reservaController.obtainReservaById(fechaHora);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(reserva, response.getBody());
    }

    @Test
    void testObtainReservaById_NotFound() {
        when(reservaService.ObtainReservaById(fechaHora)).thenReturn(Optional.empty());

        ResponseEntity<Reserva> response = reservaController.obtainReservaById(fechaHora);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testAddReserva_Success() {
        when(reservaService.CreateReserva(reserva)).thenReturn(reserva);

        ResponseEntity<Reserva> response = reservaController.addReserva(reserva);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(reserva, response.getBody());
    }

    @Test
    void testAddReserva_BadRequest() {
        when(reservaService.CreateReserva(any())).thenThrow(IllegalArgumentException.class);

        ResponseEntity<Reserva> response = reservaController.addReserva(reserva);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testUpdateReserva_Success() {
        when(reservaService.UpdateReserva(fechaHora, reserva)).thenReturn(reserva);

        ResponseEntity<Reserva> response = reservaController.updateReserva(fechaHora, reserva);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(reserva, response.getBody());
    }

    @Test
    void testUpdateReserva_NotFound() {
        when(reservaService.UpdateReserva(fechaHora, reserva)).thenThrow(IllegalArgumentException.class);

        ResponseEntity<Reserva> response = reservaController.updateReserva(fechaHora, reserva);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testDeleteReserva() {
        doNothing().when(reservaService).DeleteReserva(fechaHora);

        ResponseEntity<Void> response = reservaController.deleteReserva(fechaHora);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(reservaService, times(1)).DeleteReserva(fechaHora);
    }
}
