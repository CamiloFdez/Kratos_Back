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
        return reservaRepository.findByIdFechaHora(fechaHora);
    }

    public Reserva CreateReserva(Reserva reserva) {
        if (reservaRepository.findByIdFechaHora(reserva.getFechaHora()).isPresent()) {
            throw new IllegalArgumentException("The date " + reserva.getFechaHora() +
                    " already exists");
        }
        return reservaRepository.save(reserva);
    }

    public Reserva UpdateReserva(LocalDateTime fechaHora, Reserva reservaActualizada) {
        return reservaRepository.findByIdFechaHora(fechaHora).map(reserva -> {
            reserva.setUsuarioId(reservaActualizada.getUsuarioId());
            reserva.setLaboratorio(reservaActualizada.getLaboratorio());
            reserva.setFechaHora(reservaActualizada.getFechaHora());
            reserva.setProposito(reservaActualizada.getProposito());
            return reservaRepository.save(reserva);
        }).orElseThrow(() -> new IllegalArgumentException("Reserva not found"));
    }

    public void DeleteReserva(LocalDateTime fechaHora) { reservaRepository.deleteById(fechaHora); }
}
