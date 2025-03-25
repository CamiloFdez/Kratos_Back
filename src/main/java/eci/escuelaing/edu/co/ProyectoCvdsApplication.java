package eci.escuelaing.edu.co;

import eci.escuelaing.edu.co.models.Horario;
import eci.escuelaing.edu.co.models.Laboratorio;
import eci.escuelaing.edu.co.models.Usuario;
import eci.escuelaing.edu.co.models.Reserva;
import eci.escuelaing.edu.co.repositories.HorarioRepository;
import eci.escuelaing.edu.co.repositories.LaboratorioRepository;
import eci.escuelaing.edu.co.repositories.ReservaRepository;
import eci.escuelaing.edu.co.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "eci.escuelaing.edu.co.repositories")
public class ProyectoCvdsApplication implements CommandLineRunner {

	@Autowired
	HorarioRepository horarioRepository;

	@Autowired
	LaboratorioRepository laboratorioRepository;

	@Autowired
	ReservaRepository reservaRepository;

	@Autowired
	UsuarioRepository usuarioRepository;

	List<Horario> listaHorarios = new ArrayList<Horario>();

	public static void main(String[] args) {

		SpringApplication.run(ProyectoCvdsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		horarioRepository.deleteAll();
		laboratorioRepository.deleteAll();
		reservaRepository.deleteAll();
		usuarioRepository.deleteAll();
		crearDatosIniciales();
		}

		private void crearDatosIniciales() {
			Laboratorio laboratorio = new Laboratorio("lab1", "Laboratorio de Física", "Edificio A", 30);
			laboratorioRepository.save(laboratorio);
		
			Horario horario1 = new Horario("lab1", "Lunes", "08:00", "10:00", true);
			Horario horario2 = new Horario("lab1", "Martes", "10:00", "12:00", true);
			horarioRepository.save(horario1);
			horarioRepository.save(horario2);
		
			Usuario usuario = new Usuario("user1", "Juan Pérez", "juan@example.com", "password123");
			usuarioRepository.save(usuario);
		
			Reserva reserva = new Reserva("reserva1", "user1", laboratorio, LocalDateTime.now(), "Clase de Física", 1);
			reservaRepository.save(reserva);
		
			System.out.println("Datos iniciales creados correctamente.");
		}
		
}

