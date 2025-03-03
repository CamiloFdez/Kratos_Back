package eci.escuelaing.edu.co.services;

import eci.escuelaing.edu.co.models.Laboratorio;  // Importa la clase de modelo Laboratorio
import eci.escuelaing.edu.co.models.Horario;  // Importa la clase de modelo Horario
import eci.escuelaing.edu.co.repositories.LaboratorioRepository;  // Importa el repositorio de laboratorios
import eci.escuelaing.edu.co.services.HorarioService;  // Importa el servicio de Horario
import org.springframework.beans.factory.annotation.Autowired;  // Para la inyección de dependencias
import org.springframework.stereotype.Service;  // Para marcar la clase como un servicio

import java.util.List;  // Para manejar listas de laboratorios
import java.util.Optional;  // Para manejar valores opcionales

@Service
public class LaboratorioService {

    private final LaboratorioRepository laboratorioRepository;
    private final HorarioService horarioService;  // Inyecta el servicio de Horario

    @Autowired
    public LaboratorioService(LaboratorioRepository laboratorioRepository, HorarioService horarioService) {
        this.laboratorioRepository = laboratorioRepository;
        this.horarioService = horarioService;
    }

    // Obtener todos los laboratorios
    public List<Laboratorio> getAllLaboratorios() {
        return laboratorioRepository.findAll();
    }

    // Obtener un laboratorio por su id
    public Optional<Laboratorio> getLaboratorioById(String labId) {
        return laboratorioRepository.findById(labId);
    }

    // Crear o actualizar un laboratorio
    public Laboratorio saveOrUpdateLaboratorio(Laboratorio laboratorio) {
        return laboratorioRepository.save(laboratorio);
    }

    // Eliminar un laboratorio por id
    public void deleteLaboratorio(String labId) {
        laboratorioRepository.deleteById(labId);
    }

    // Obtener los horarios disponibles para un laboratorio específico
    public List<Horario> obtenerHorariosDisponibles(String laboratorioId) {
        // Verifica si el laboratorio existe
        Optional<Laboratorio> laboratorio = laboratorioRepository.findById(laboratorioId);

        if (laboratorio.isPresent()) {
            // Si el laboratorio existe, obtenemos los horarios disponibles a través del servicio HorarioService
            return horarioService.getHorariosByLabId(laboratorioId);  // Obtiene los horarios de este laboratorio
        } else {
            // Si el laboratorio no existe, puedes devolver una lista vacía o manejar el error de alguna otra manera
            return List.of();  // Retorna una lista vacía si no se encuentra el laboratorio
        }
    }
}
