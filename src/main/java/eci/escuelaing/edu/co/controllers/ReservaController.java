package eci.escuelaing.edu.co.controllers;

import eci.escuelaing.edu.co.models.Reserva;
import eci.escuelaing.edu.co.services.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservas")
public class ReservaController {
    @Autowired
    private ReservaService reservaService;

    @GetMapping
    public List<Reserva> listReservas() {
        return reservaService.ObtainAllReservas();
    }

    @GetMapping("/{fechaHora}")
    public ResponseEntity<Reserva> obtainReservaById(@PathVariable LocalDateTime fechaHora) {
        Optional<Reserva> reserva = reservaService.ObtainReservaById(fechaHora);
        return reserva.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Reserva> addReserva(@RequestBody Reserva reserva) {
        try {
            // Verifica la prioridad, si no es válida, lanza una excepción
            if (reserva.getPrioridad() < 1 || reserva.getPrioridad() > 5) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(null);  // O podrías retornar un mensaje de error más específico
            }

            // Crea la reserva
            Reserva newReserva = reservaService.CreateReserva(reserva);
            return ResponseEntity.status(HttpStatus.CREATED).body(newReserva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/{fechaHora}")
    public ResponseEntity<Reserva> updateReserva(@PathVariable LocalDateTime fechaHora,
                                                 @RequestBody Reserva reserva) {
        try {
            // Verifica la prioridad, si no es válida, lanza una excepción
            if (reserva.getPrioridad() < 1 || reserva.getPrioridad() > 5) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(null);  // O podrías retornar un mensaje de error más específico
            }

            // Actualiza la reserva
            Reserva updatedReserva = reservaService.UpdateReserva(fechaHora, reserva);
            return ResponseEntity.ok(updatedReserva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{fechaHora}")
    public ResponseEntity<Void> deleteReserva(@PathVariable LocalDateTime fechaHora) {
        reservaService.DeleteReserva(fechaHora);
        return ResponseEntity.noContent().build();
    }
}
