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
		Laboratorio laboratorio1 = new Laboratorio("lab1", "Aula Interactiva", "Edificio A", 30);
		laboratorioRepository.save(laboratorio1);
		Laboratorio laboratorio2 = new Laboratorio("lab2", "Aula Multimedia y movies", "Edificio E", 30);
		laboratorioRepository.save(laboratorio2);
		Laboratorio laboratorio3 = new Laboratorio("lab3", "Practica Libre", "Edificio A", 30);
		laboratorioRepository.save(laboratorio3);
		Laboratorio laboratorio4 = new Laboratorio("lab4", "Ingenieria de Software", "Edificio A", 30);
		laboratorioRepository.save(laboratorio4);
		Laboratorio laboratorio5 = new Laboratorio("lab5", "Plataformas Computacionales", "Edificio B", 30);
		laboratorioRepository.save(laboratorio5);
		Laboratorio laboratorio6 = new Laboratorio("lab6", "Redes de Compitadores", "Edificio B", 30);
		laboratorioRepository.save(laboratorio6);
		Laboratorio laboratorio7 = new Laboratorio("lab7", "Fundamentos Computacionales", "Edificio A", 30);
		laboratorioRepository.save(laboratorio7);
		Laboratorio laboratorio8 = new Laboratorio("lab8", "Realidad Aumentada y Desarrollo de Videojuegos", "Edificio A", 30);
		laboratorioRepository.save(laboratorio8);
		Laboratorio laboratorio9 = new Laboratorio("lab9", "Aula de Estrategias Digitales Para La Formacion Informatica", "Edificio A", 30);
		laboratorioRepository.save(laboratorio9);
		Horario horario1 = new Horario("lab1", "Lunes", "08:30", "10:00", true);
		Horario horario2 = new Horario("lab2", "Martes", "10:00", "11:30", true);
		Horario horario3 = new Horario("lab3", "Martes", "11:30", "01:00", true);
		Horario horario4 = new Horario("lab4", "Miercoles", "07:00", "08:30", true);
		Horario horario5 = new Horario("lab5", "Jueves", "08:30", "11:30", true);
		Horario horario6 = new Horario("lab6", "Viernes", "10:00", "11:30", true);
		Horario horario7 = new Horario("lab7", "Lunes", "10:00", "11:30", true);
		Horario horario8 = new Horario("lab8", "Martes", "10:00", "11:30", true);
		Horario horario9 = new Horario("lab9", "Martes", "08:30", "11:30", true);
		horarioRepository.save(horario1);
		horarioRepository.save(horario2);
		horarioRepository.save(horario3);
		horarioRepository.save(horario4);
		horarioRepository.save(horario5);
		horarioRepository.save(horario6);
		horarioRepository.save(horario7);
		horarioRepository.save(horario8);
		horarioRepository.save(horario9);
		Usuario usuario = new Usuario("user1", "Juan Pérez", "juan@mail.escuelaing.edu.co", "password123");
		usuarioRepository.save(usuario);
		Reserva reserva = new Reserva("reserva1", "user1", laboratorio, LocalDateTime.now(), "Clase de Física", 1);
		reservaRepository.save(reserva);
		System.out.println("Datos iniciales creados correctamente.");
	}
}

