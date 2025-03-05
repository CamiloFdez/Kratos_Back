package eci.escuelaing.edu.co.controllers;


import eci.escuelaing.edu.co.models.Horario;
import eci.escuelaing.edu.co.services.HorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/horarios")
public class HorarioController {

    private final HorarioService horarioService;

    @Autowired
    public HorarioController(HorarioService horarioService) {
        this.horarioService = horarioService;
    }

    // Método para obtener los horarios disponibles por laboratorio
    @GetMapping("/{labId}")
    public ResponseEntity<List<Horario>> obtainHorarioById(@PathVariable("labId") String labId) {
        List<Horario> horarios = horarioService.getHorariosByLabId(labId);

        if (horarios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(horarios, HttpStatus.OK);
    }

    // Método para actualizar la disponibilidad de los horarios
    @PutMapping("/{labId}")
    public ResponseEntity<Horario> updateHorario(@PathVariable("labId") String labId, @RequestBody Horario horario) {
        Optional<Horario> updatedHorario = Optional.ofNullable(horarioService.updateHorario(labId, horario));

        if (!updatedHorario.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(updatedHorario.get(), HttpStatus.OK);
    }
}
