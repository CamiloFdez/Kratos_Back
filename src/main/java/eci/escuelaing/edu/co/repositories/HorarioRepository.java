package eci.escuelaing.edu.co.repositories;

import eci.escuelaing.edu.co.models.Horario;  // Importa la clase de modelo Horario
import org.springframework.data.mongodb.repository.MongoRepository;  // Para interactuar con MongoDB
import java.util.List;  // Para manejar listas de horarios

public interface HorarioRepository extends MongoRepository<Horario, String> {

    List<Horario> findByLabId(String labId);
}