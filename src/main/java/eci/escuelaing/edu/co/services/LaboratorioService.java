package eci.escuelaing.edu.co.services;

import eci.escuelaing.edu.co.models.Laboratorio;
import eci.escuelaing.edu.co.models.Horario;
import eci.escuelaing.edu.co.repositories.LaboratorioRepository;
import eci.escuelaing.edu.co.services.HorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LaboratorioService {

    private final LaboratorioRepository laboratorioRepository;
    private final HorarioService horarioService;
    @Autowired
    public LaboratorioService(LaboratorioRepository laboratorioRepository, HorarioService horarioService) {
        this.laboratorioRepository = laboratorioRepository;
        this.horarioService = horarioService;
    }

    public List<Laboratorio> getAllLaboratorios() {
        return laboratorioRepository.findAll();
    }

    public Optional<Laboratorio> getLaboratorioById(String labId) {
        return laboratorioRepository.findById(labId);
    }

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
