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

    public void deleteLaboratorio(String id) {
        Optional<Laboratorio> lab = laboratorioRepository.findById(id);
        if (lab.isEmpty()) {
            throw new IllegalArgumentException("Laboratorio no encontrado");
        }
        laboratorioRepository.deleteById(id);
    }

    public List<Horario> obtenerHorariosDisponibles(String laboratorioId) {
        Optional<Laboratorio> laboratorio = laboratorioRepository.findById(laboratorioId);

        if (laboratorio.isEmpty()) {
            throw new IllegalArgumentException("No se encontró el laboratorio con ID " + laboratorioId);
        }

        return horarioService.getHorariosByLabId(laboratorioId);
    }

}
