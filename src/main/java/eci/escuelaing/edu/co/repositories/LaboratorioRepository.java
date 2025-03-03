package eci.escuelaing.edu.co.repositories;


import eci.escuelaing.edu.co.models.Laboratorio;  // Importa la clase de modelo Laboratorio
import org.springframework.data.mongodb.repository.MongoRepository;  // Para interactuar con MongoDB

public interface LaboratorioRepository extends MongoRepository<Laboratorio, String> {


}
