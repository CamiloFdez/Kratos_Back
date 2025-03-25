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
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/laboratorios")
public class LaboratorioController {

    private final LaboratorioService laboratorioService;
    private final HorarioService horarioService;
    private static final Pattern LAB_ID_PATTERN = Pattern.compile("LABSIS-[0-9]{3}");

    @Autowired
    public LaboratorioController(LaboratorioService laboratorioService, HorarioService horarioService) {
        this.laboratorioService = laboratorioService;
        this.horarioService = horarioService;
    }

    // Obtener todos los laboratorios de sistemas con formato válido
    @GetMapping("/")
    public ResponseEntity<List<Laboratorio>> obtenerLaboratorios() {
        List<Laboratorio> laboratorios = laboratorioService.getAllLaboratorios()
                .stream()
                .filter(lab -> lab.getTipo().equalsIgnoreCase("sistemas") && 
                              LAB_ID_PATTERN.matcher(lab.getId()).matches())
                .toList();
        
        return laboratorios.isEmpty() ? 
               ResponseEntity.noContent().build() : 
               ResponseEntity.ok(laboratorios);
    }

    // Obtener horarios disponibles de un laboratorio específico
    @GetMapping("/{labId}/horarios")
    public ResponseEntity<List<Horario>> obtenerHorariosDisponibles(@PathVariable String labId) {
        if (!esLaboratorioValido(labId)) {
            return ResponseEntity.badRequest().build();
        }
        
        List<Horario> horarios = horarioService.getHorariosByLabId(labId)
                .stream()
                .filter(Horario::isDisponible)
                .toList();
                
        return horarios.isEmpty() ? 
               ResponseEntity.noContent().build() : 
               ResponseEntity.ok(horarios);
    }

    // Actualizar un horario específico
    @PutMapping("/{labId}/horarios")
    public ResponseEntity<Horario> actualizarHorario(
            @PathVariable String labId, 
            @RequestBody Horario horario) {
        
        if (!esLaboratorioValido(labId) || !labId.equals(horario.getLabId())) {
            return ResponseEntity.badRequest().build();
        }
        
        try {
            Horario horarioActualizado = horarioService.updateHorario(labId, horario);
            return ResponseEntity.ok(horarioActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar un horario específico
    @DeleteMapping("/{labId}/horarios/{horarioId}")
    public ResponseEntity<Void> eliminarHorario(
            @PathVariable String labId, 
            @PathVariable String horarioId) {
        
        if (!esLaboratorioValido(labId)) {
            return ResponseEntity.badRequest().build();
        }
        
        Optional<Horario> horario = horarioService.getHorarioById(horarioId);
        if (horario.isEmpty() || !horario.get().getLabId().equals(labId)) {
            return ResponseEntity.notFound().build();
        }
        
        horarioService.deleteHorario(horarioId);
        return ResponseEntity.noContent().build();
    }

    // Reservar un laboratorio
    @PostMapping("/{labId}/reservar")
    public ResponseEntity<Horario> reservarLaboratorio(
            @PathVariable String labId, 
            @RequestBody Horario horario) {
        
        if (!esLaboratorioValido(labId) || !labId.equals(horario.getLabId())) {
            return ResponseEntity.badRequest().build();
        }
        
        if (!horario.isDisponible()) {
            return ResponseEntity.badRequest().body(null);
        }
        
        Horario horarioReservado = horarioService.actualizarDisponibilidad(horario, false);
        return ResponseEntity.ok(horarioReservado);
    }

    // Cancelar una reserva
    @PostMapping("/{labId}/cancelar")
    public ResponseEntity<Horario> cancelarReserva(
            @PathVariable String labId, 
            @RequestBody Horario horario) {
        
        if (!esLaboratorioValido(labId) || !labId.equals(horario.getLabId())) {
            return ResponseEntity.badRequest().build();
        }
        
        if (horario.isDisponible()) {
            return ResponseEntity.badRequest().body(null);
        }
        
        Horario horarioCancelado = horarioService.actualizarDisponibilidad(horario, true);
        return ResponseEntity.ok(horarioCancelado);
    }

    // Método de validación mejorado
    private boolean esLaboratorioValido(String labId) {
        if (!LAB_ID_PATTERN.matcher(labId).matches()) {
            return false;
        }
        
        Optional<Laboratorio> lab = laboratorioService.getLaboratorioById(labId);
        return lab.isPresent() && lab.get().getTipo().equalsIgnoreCase("sistemas");
    }
}