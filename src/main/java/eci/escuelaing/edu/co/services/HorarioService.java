package eci.escuelaing.edu.co.services;

import eci.escuelaing.edu.co.models.Horario;
import eci.escuelaing.edu.co.repositories.HorarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HorarioService {

    private final HorarioRepository horarioRepository;

    @Autowired
    public HorarioService(HorarioRepository horarioRepository) {
        this.horarioRepository = horarioRepository;
    }

    // Obtener todos los horarios
    public List<Horario> getAllHorarios() {
        return horarioRepository.findAll();
    }

    // Obtener horarios por el id del laboratorio
    public List<Horario> getHorariosByLabId(String labId) {
        return horarioRepository.findByLabId(labId);  // Llama al repositorio para obtener los horarios del laboratorio
    }

    // Obtener un horario específico por su id
    public Optional<Horario> getHorarioById(String horarioId) {
        return horarioRepository.findById(horarioId);
    }

    // Guardar o actualizar un horario
    public Horario saveOrUpdateHorario(Horario horario) {
        return horarioRepository.save(horario);  // Guarda o actualiza un horario en la base de datos
    }

    // Eliminar un horario por id
    public void deleteHorario(String horarioId) {
        horarioRepository.deleteById(horarioId);  // Elimina el horario por su id
    }

    // Actualizar la disponibilidad de un horario (para manejar las reservas)
    public Horario actualizarDisponibilidad(Horario horario, boolean disponibilidad) {
        horario.setDisponible(disponibilidad);  // Actualiza la disponibilidad
        return horarioRepository.save(horario);  // Guarda el horario actualizado
    }

    public Horario updateHorario(String labId, Horario horario) {
        List<Horario> horarios = horarioRepository.findByLabId(labId);

        if (horarios.isEmpty()) {
            throw new RuntimeException("No se encontraron horarios para el laboratorio con id: " + labId);
        }

        for (Horario h : horarios) {
            if (h.getId().equals(horario.getId())) {
                h.setDia(horario.getDia());
                h.setHoraInicio(horario.getHoraInicio());
                h.setHoraFin(horario.getHoraFin());
                h.setDisponible(horario.isDisponible());

                return horarioRepository.save(h);
            }
        }

        throw new RuntimeException("Horario con id " + horario.getId() + " no encontrado para el laboratorio " + labId);
    }
}
