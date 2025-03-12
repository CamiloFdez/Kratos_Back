package eci.escuelaing.edu.co.controllers;

import eci.escuelaing.edu.co.models.Usuario;
import eci.escuelaing.edu.co.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
        usuario = new Usuario("U123", "Juan Pérez", "juan.perez@example.com", "12345678");
    }

    @Test
    void listUsers_ShouldReturnAllUsers() throws Exception {
        when(usuarioService.ObtainAllUsers()).thenReturn(Arrays.asList(usuario));

        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value("U123"));
    }

    @Test
    void obtainUserById_ShouldReturnUser_WhenUserExists() throws Exception {
        Usuario usuario = new Usuario("U123", "Juan Pérez", "juan@example.com", "password123"); 
        when(usuarioService.ObtainUserById("U123")).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/usuarios/U123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("U123"))
                .andExpect(jsonPath("$.name").value("Juan Pérez")); 
    }


    @Test
    void obtainUserById_ShouldReturnNotFound_WhenUserDoesNotExist() throws Exception {
        when(usuarioService.ObtainUserById("U999")).thenReturn(Optional.empty());

        mockMvc.perform(get("/usuarios/U999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addUser_ShouldCreateUser() throws Exception {
        when(usuarioService.CreateUser(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"U123\",\"nombre\":\"Juan Pérez\",\"email\":\"juan.perez@example.com\",\"password\":\"12345678\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("U123"));
    }

    @Test
    void updateUser_ShouldUpdateUser_WhenUserExists() throws Exception {
        when(usuarioService.UpdateUser(eq("U123"), any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(put("/usuarios/U123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"U123\",\"nombre\":\"Juan Pérez\",\"email\":\"juan.perez@example.com\",\"password\":\"12345678\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("U123"));
    }

    @Test
    void updateUser_ShouldReturnNotFound_WhenUserDoesNotExist() throws Exception {
        when(usuarioService.UpdateUser(eq("U999"), any(Usuario.class))).thenThrow(new IllegalArgumentException());

        mockMvc.perform(put("/usuarios/U999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"U999\",\"nombre\":\"Juan Pérez\",\"email\":\"juan.perez@example.com\",\"password\":\"12345678\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUser_ShouldReturnNoContent() throws Exception {
        doNothing().when(usuarioService).DeleteUser("U123");

        mockMvc.perform(delete("/usuarios/U123"))
                .andExpect(status().isNoContent());
    }
}