package eci.escuelaing.edu.co.services;

import eci.escuelaing.edu.co.models.Reserva;
import eci.escuelaing.edu.co.repositories.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {
    @Autowired
    private ReservaRepository reservaRepository;

    public List<Reserva> ObtainAllReservas() {
        return reservaRepository.findAll();
    }

    public Optional<Reserva> ObtainReservaById(LocalDateTime fechaHora) {
        return reservaRepository.findByFechaHora(fechaHora);
    }

    public Reserva CreateReserva(Reserva reserva) {
        if (reserva.getPrioridad() < 1 || reserva.getPrioridad() > 5) {
            throw new IllegalArgumentException("La prioridad debe estar entre 1 y 5.");
        }

        if (reservaRepository.findByFechaHora(reserva.getFechaHora()).isPresent()) {
            throw new IllegalArgumentException("The date " + reserva.getFechaHora() + " already exists");
        }

        return reservaRepository.save(reserva);
    }

    public Reserva UpdateReserva(LocalDateTime fechaHora, Reserva reservaActualizada) {
        if (reservaActualizada.getPrioridad() < 1 || reservaActualizada.getPrioridad() > 5) {
            throw new IllegalArgumentException("La prioridad debe estar entre 1 y 5.");
        }

        return reservaRepository.findByFechaHora(fechaHora).map(reserva -> {
            reserva.setUsuarioId(reservaActualizada.getUsuarioId());
            reserva.setLaboratorio(reservaActualizada.getLaboratorio());
            reserva.setFechaHora(reservaActualizada.getFechaHora());
            reserva.setProposito(reservaActualizada.getProposito());
            reserva.setPrioridad(reservaActualizada.getPrioridad()); // Actualiza la prioridad
            return reservaRepository.save(reserva);
        }).orElseThrow(() -> new IllegalArgumentException("Reserva not found"));
    }

    public void DeleteReserva(LocalDateTime fechaHora) {
        reservaRepository.deleteByFechaHora(fechaHora);
    }

}
