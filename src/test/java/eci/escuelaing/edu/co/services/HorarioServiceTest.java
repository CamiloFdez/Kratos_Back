package eci.escuelaing.edu.co.services;

import eci.escuelaing.edu.co.models.Horario;
import eci.escuelaing.edu.co.repositories.HorarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HorarioServiceTest {

    @Mock
    private HorarioRepository horarioRepository;

    @InjectMocks
    private HorarioService horarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllHorarios() {
        Horario h1 = new Horario("lab1", "Lunes", "08:00", "10:00", true);
        Horario h2 = new Horario("lab2", "Martes", "10:00", "12:00", false);
        when(horarioRepository.findAll()).thenReturn(Arrays.asList(h1, h2));
        List<Horario> horarios = horarioService.getAllHorarios();
        assertEquals(2, horarios.size());
        verify(horarioRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnHorariosByLabId() {
        String labId = "lab123";
        List<Horario> horarios = Arrays.asList(
                new Horario(labId, "Lunes", "08:00", "10:00", true),
                new Horario(labId, "Martes", "10:00", "12:00", true)
        );
        when(horarioRepository.findByLabId(labId)).thenReturn(horarios);
        List<Horario> result = horarioService.getHorariosByLabId(labId);
        assertEquals(2, result.size());
        verify(horarioRepository, times(1)).findByLabId(labId);
    }

    @Test
    void shouldReturnHorarioById() {
        Horario h = new Horario("lab1", "Miercoles", "14:00", "16:00", true);
        when(horarioRepository.findById("1")).thenReturn(Optional.of(h));
        Optional<Horario> result = horarioService.getHorarioById("1");
        assertTrue(result.isPresent());
        assertEquals("Miercoles", result.get().getDia());
        verify(horarioRepository, times(1)).findById("1");
    }

    @Test
    void shouldCreateOrUpdateHorario() {
        Horario h = new Horario("lab2", "Jueves", "16:00", "18:00", true);
        when(horarioRepository.save(h)).thenReturn(h);
        Horario result = horarioService.saveOrUpdateHorario(h);
        assertEquals(h, result);
        verify(horarioRepository, times(1)).save(h);
    }

    @Test
    void shouldDeleteHorario() {
        String horarioId = "1";
        when(horarioRepository.existsById(horarioId)).thenReturn(true);
        doNothing().when(horarioRepository).deleteById(horarioId);
        horarioService.deleteHorario(horarioId);
        verify(horarioRepository, times(1)).deleteById(horarioId);
    }

    @Test
    void shouldUpdateHorarioAvailability() {
        Horario h = new Horario("lab1", "Viernes", "18:00", "20:00", false);
        when(horarioRepository.save(h)).thenReturn(h);
        Horario updated = horarioService.actualizarDisponibilidad(h, true);
        assertTrue(updated.isDisponible());
        verify(horarioRepository, times(1)).save(h);
    }
}

