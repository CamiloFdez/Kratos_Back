package eci.escuelaing.edu.co.services;
import eci.escuelaing.edu.co.models.Horario;
import eci.escuelaing.edu.co.models.Laboratorio;
import eci.escuelaing.edu.co.services.HorarioService;
import eci.escuelaing.edu.co.repositories.LaboratorioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class LaboratorioServiceTest {
    @Mock
    private LaboratorioRepository laboratorioRepository;

    @Mock
    private HorarioService horarioService;

    @InjectMocks
    private LaboratorioService laboratorioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllLaboratorios() {
        Laboratorio lab1 = new Laboratorio("1","lab1","Edificio B",20);
        Laboratorio lab2 = new Laboratorio("2","lab2","Edificio H",30);
        when(laboratorioRepository.findAll()).thenReturn(Arrays.asList(lab1,lab2));
        List<Laboratorio> laboratorios = laboratorioService.getAllLaboratorios();
        assertEquals(2,laboratorios.size());
        verify(laboratorioRepository,times(1)).findAll();
    }

    @Test
    void ShouldReturnLaboratorioById(){
        Laboratorio lab3 = new Laboratorio("3","lab3","Edificio B",25);
        when(laboratorioRepository.findById("3")).thenReturn(Optional.of(lab3));
        Optional<Laboratorio> result = laboratorioService.getLaboratorioById("3");
        assertTrue(result.isPresent());
        assertEquals("lab3",result.get().getNombre());
        verify(laboratorioRepository,times(1)).findById("3");
    }

    @Test
    void ShoulCreateOrUpdateLaboratorio(){
        Laboratorio lab4 = new Laboratorio("4","lab4","Edificio B",23);
        when(laboratorioRepository.save(lab4)).thenReturn(lab4);
        Laboratorio result = laboratorioService.saveOrUpdateLaboratorio(lab4);
        assertEquals(lab4,result);
        verify(laboratorioRepository,times(1)).save(lab4);
    }

    @Test
    void ShouldDeleteLaboratorio(){
        when(laboratorioRepository.existsById("1")).thenReturn(true);
        laboratorioService.deleteLaboratorio("1");
        verify(laboratorioRepository,times(1)).deleteById("1");
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingLaboratorio() {
        // Arrange
        when(laboratorioRepository.existsById("1")).thenReturn(false);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> laboratorioService.deleteLaboratorio("1"));
        verify(laboratorioRepository, times(1)).existsById("1");
    }

    @Test
    void shouldReturnHorariosDisponibles() {
        // Arrange
        String laboratorioId = "lab123";
        Laboratorio laboratorio = new Laboratorio(laboratorioId, "Laboratorio de Química", "Edificio H",30);
        List<Horario> horarios = Arrays.asList(
                new Horario(laboratorioId, "Lunes", "08:00", "10:00", true),
                new Horario(laboratorioId, "Martes", "10:00", "12:00", true)
        );

        when(laboratorioRepository.findById(laboratorioId)).thenReturn(Optional.of(laboratorio));
        when(horarioService.getHorariosByLabId(laboratorioId)).thenReturn(horarios);

        List<Horario> result = laboratorioService.obtenerHorariosDisponibles(laboratorioId);

        assertEquals(2, result.size());
        assertEquals("Lunes", result.get(0).getDia());
        assertEquals("08:00", result.get(0).getHoraInicio());
        assertEquals("10:00", result.get(0).getHoraFin());
        assertTrue(result.get(0).isDisponible());

        assertEquals("Martes", result.get(1).getDia());
        assertEquals("10:00", result.get(1).getHoraInicio());
        assertEquals("12:00", result.get(1).getHoraFin());
        assertTrue(result.get(1).isDisponible());

        verify(laboratorioRepository, times(1)).findById(laboratorioId);
        verify(horarioService, times(1)).getHorariosByLabId(laboratorioId);
    }


    @Test
    void shouldThrowExceptionWhenLaboratorioNotFoundForHorarios() {
        String laboratorioId = "lab123";
        when(laboratorioRepository.findById(laboratorioId)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> laboratorioService.obtenerHorariosDisponibles(laboratorioId));
        verify(laboratorioRepository, times(1)).findById(laboratorioId);
    }

}