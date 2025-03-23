package eci.escuelaing.edu.co.services;

import eci.escuelaing.edu.co.models.Laboratorio;
import eci.escuelaing.edu.co.models.Reserva;
import eci.escuelaing.edu.co.repositories.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Random;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class ReservaService {
    @Autowired
    private ReservaRepository reservaRepository;
    private final Random random = new Random();

    public List<Reserva> ObtainAllReservas() {
        return reservaRepository.findAll();
    }

    public Optional<Reserva> ObtainReservaByFechaHora(LocalDateTime fechaHora) {
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
            reserva.setPrioridad(reservaActualizada.getPrioridad());
            return reservaRepository.save(reserva);
        }).orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada"));
    }

    public void DeleteReserva(LocalDateTime fechaHora) {
        reservaRepository.deleteByFechaHora(fechaHora);
    }

    public List<Reserva> generarReservasAleatorias() {
        int cantidad = random.nextInt(901) + 100; // Genera entre 100 y 1000 reservas
        List<Reserva> nuevasReservas = new ArrayList<>();

        for (int i = 0; i < cantidad; i++) {
            Reserva reserva = new Reserva();
            reserva.setUsuarioId("usuario" + (i + 1));

            Laboratorio laboratorio = new Laboratorio(
                    "lab-" + ((i % 10) + 1),
                    "Laboratorio " + ((i % 10) + 1),
                    "Ubicación " + ((i % 5) + 1),
                    random.nextInt(50) + 10
            );
            reserva.setLaboratorio(laboratorio);
            reserva.setFechaHora(LocalDateTime.now().plusDays(random.nextInt(150)));
            reserva.setProposito("Reserva generada automáticamente");
            reserva.setPrioridad(random.nextInt(5) + 1);

            nuevasReservas.add(reserva);
        }
        return reservaRepository.saveAll(nuevasReservas);
    }

    public Map<String, Object> obtenerEstadisticasReservas() {
        List<Reserva> reservas = reservaRepository.findAll();

        Map<String, Long> histogramaReservas = reservas.stream()
                .collect(Collectors.groupingBy(r -> r.getFechaHora().toLocalDate().toString(), Collectors.counting()));

        Map<String, Long> reservasPorLaboratorio = reservas.stream()
                .collect(Collectors.groupingBy(r -> r.getLaboratorio().getNombre(), Collectors.counting()));

        double promedioPrioridad = reservas.stream()
                .collect(Collectors.averagingInt(Reserva::getPrioridad));

        List<Map<String, Object>> laboratoriosMasDemandados = reservasPorLaboratorio.entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .limit(5)
                .map(entry -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("nombre", entry.getKey());
                    map.put("cantidad", entry.getValue());
                    return map;
                })
                .collect(Collectors.toList());

        Map<String, Object> estadisticas = Map.of(
                "histogramaReservas", histogramaReservas,
                "reservasPorLaboratorio", reservasPorLaboratorio,
                "promedioPrioridad", promedioPrioridad,
                "laboratoriosMasDemandados", laboratoriosMasDemandados
        );

        return estadisticas;
    }
}
