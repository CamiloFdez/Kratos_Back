package eci.escuelaing.edu.co.controllers;

import eci.escuelaing.edu.co.models.Horario;
import eci.escuelaing.edu.co.services.HorarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class HorarioControllerTest {

    private MockMvc mockMvc;

    @Mock
    private HorarioService horarioService;

    @InjectMocks
    private HorarioController horarioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(horarioController).build();
    }

    @Test
    void shouldReturnHorariosByLabId() throws Exception {
        String labId = "lab1";
        List<Horario> horarios = List.of(new Horario(labId, "Lunes", "08:00", "10:00", true));

        when(horarioService.getHorariosByLabId(labId)).thenReturn(horarios);

        mockMvc.perform(get("/api/horarios/{labId}", labId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].labId").value(labId))
                .andExpect(jsonPath("$[0].dia").value("Lunes"))
                .andExpect(jsonPath("$[0].horaInicio").value("08:00"));

        verify(horarioService, times(1)).getHorariosByLabId(labId);
    }

    @Test
    void shouldReturnNotFoundWhenNoHorariosExist() throws Exception {
        String labId = "lab2";

        when(horarioService.getHorariosByLabId(labId)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/horarios/{labId}", labId))
                .andExpect(status().isNotFound());

        verify(horarioService, times(1)).getHorariosByLabId(labId);
    }

    @Test
    void shouldUpdateHorarioSuccessfully() throws Exception {
        String labId = "lab1";
        Horario horario = new Horario(labId, "Martes", "09:00", "11:00", false);
        horario.setId("horario1");

        when(horarioService.updateHorario(eq(labId), any(Horario.class))).thenReturn(horario);

        mockMvc.perform(put("/api/horarios/{labId}", labId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"horario1\",\"labId\":\"lab1\",\"dia\":\"Martes\",\"horaInicio\":\"09:00\",\"horaFin\":\"11:00\",\"disponible\":false}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("horario1"))
                .andExpect(jsonPath("$.dia").value("Martes"))
                .andExpect(jsonPath("$.horaInicio").value("09:00"))
                .andExpect(jsonPath("$.disponible").value(false));

        verify(horarioService, times(1)).updateHorario(eq(labId), any(Horario.class));
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonExistingHorario() throws Exception {
        String labId = "lab3";
        Horario horario = new Horario(labId, "Miércoles", "10:00", "12:00", true);
        horario.setId("horarioX");

        when(horarioService.updateHorario(eq("horarioX"), any(Horario.class)))
                .thenThrow(new RuntimeException("Horario con id horarioX no encontrado"));

        mockMvc.perform(put("/api/horarios/{labId}", labId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"horarioX\",\"labId\":\"lab3\",\"dia\":\"Miércoles\",\"horaInicio\":\"10:00\",\"horaFin\":\"12:00\",\"disponible\":true}"))
                .andExpect(status().isNotFound());

        verify(horarioService, times(1)).updateHorario(eq(labId), any(Horario.class));
    }
}
