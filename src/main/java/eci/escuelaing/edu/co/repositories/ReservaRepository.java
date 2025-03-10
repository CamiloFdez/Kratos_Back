package eci.escuelaing.edu.co.repositories;

import eci.escuelaing.edu.co.models.Reserva;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ReservaRepository extends MongoRepository <Reserva, LocalDateTime> {
    Optional<Reserva> findByIdFechaHora(LocalDateTime fechaHora);


    Optional<Reserva> findById(String number);
}
