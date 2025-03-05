package eci.escuelaing.edu.co.controllers;

import eci.escuelaing.edu.co.models.Horario;
import eci.escuelaing.edu.co.models.Laboratorio;
import eci.escuelaing.edu.co.services.HorarioService;
import eci.escuelaing.edu.co.services.LaboratorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/laboratorios")
public class LaboratorioController {

    private final LaboratorioService laboratorioService;
    private final HorarioService horarioService;

    @Autowired
    public LaboratorioController(LaboratorioService laboratorioService, HorarioService horarioService) {
        this.laboratorioService = laboratorioService;
        this.horarioService = horarioService;
    }

    // Endpoint para obtener todos los laboratorios
    @GetMapping("/")
    public ResponseEntity<List<Laboratorio>> obtenerLaboratorios() {
        List<Laboratorio> laboratorios = laboratorioService.getAllLaboratorios();
        if (laboratorios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(laboratorios);
    }

    // Endpoint para obtener los horarios disponibles de un laboratorio
    @GetMapping("/{labId}/horarios")
    public ResponseEntity<List<Horario>> obtenerHorariosDisponibles(@PathVariable String labId) {
        List<Horario> horarios = horarioService.getHorariosByLabId(labId);
        if (horarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(horarios);
    }

    // Endpoint para realizar una actualización de un horario
    @PutMapping("/{labId}/horarios")
    public ResponseEntity<Horario> actualizarHorario(@PathVariable String labId, @RequestBody Horario horario) {
        try {
            Horario horarioActualizado = horarioService.updateHorario(labId, horario);
            return ResponseEntity.ok(horarioActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);  // Si no se encuentra el horario o el laboratorio
        }
    }

    // Endpoint para eliminar un horario de un laboratorio
    @DeleteMapping("/{labId}/horarios/{horarioId}")
    public ResponseEntity<Void> eliminarHorario(@PathVariable String labId, @PathVariable String horarioId) {
        try {
            horarioService.deleteHorario(horarioId);
            return ResponseEntity.noContent().build();  // Responde con un 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).build();  // Si no se encuentra el horario o el laboratorio
        }
    }

    // Endpoint para reservar un laboratorio (aquí solo actualizaríamos el estado de disponibilidad del horario)
    @PostMapping("/{labId}/reservar")
    public ResponseEntity<Horario> reservarLaboratorio(@PathVariable String labId, @RequestBody Horario horario) {
        // Actualizamos la disponibilidad del horario a false (reservado)
        Horario horarioReservado = horarioService.actualizarDisponibilidad(horario, false);
        return ResponseEntity.ok(horarioReservado);
    }

    // Endpoint para cancelar una reserva de un laboratorio
    @PostMapping("/{labId}/cancelar")
    public ResponseEntity<Horario> cancelarReserva(@PathVariable String labId, @RequestBody Horario horario) {
        // Actualizamos la disponibilidad del horario a true (liberado)
        Horario horarioCancelado = horarioService.actualizarDisponibilidad(horario, true);
        return ResponseEntity.ok(horarioCancelado);
    }
}

