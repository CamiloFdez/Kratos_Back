package eci.escuelaing.edu.co.horarios;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class HorarioIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testEliminarHorarioEndpoint() throws Exception {
        mockMvc.perform(delete("/eliminar")
                .param("laboratorio", "Lab1")
                .param("fecha", "2024-02-27T10:00:00"))
                .andExpect(status().isOk())
                .andExpect(content().string("Horario eliminado correctamente."));
    }
}
