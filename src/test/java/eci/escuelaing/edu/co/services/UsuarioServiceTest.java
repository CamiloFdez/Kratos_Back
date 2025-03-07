package eci.escuelaing.edu.co.services;

import eci.escuelaing.edu.co.models.Usuario;
import eci.escuelaing.edu.co.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllUsers() {
        // Arrange
        Usuario user1 = new Usuario("1", "Juan", "juan@mail.com", "1234");
        Usuario user2 = new Usuario("2", "Maria", "maria@mail.com", "5678");
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        // Act
        var users = usuarioService.ObtainAllUsers();

        // Assert
        assertEquals(2, users.size());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnUserById() {
        // Arrange
        Usuario user = new Usuario("1", "Juan", "juan@mail.com", "1234");
        when(usuarioRepository.findById("1")).thenReturn(Optional.of(user));

        // Act
        var result = usuarioService.ObtainUserById("1");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Juan", result.get().getName());
        verify(usuarioRepository, times(1)).findById("1");
    }

    @Test
    void shouldCreateUser() {
        // Arrange
        Usuario user = new Usuario("1", "Pedro", "pedro@mail.com", "abcd");
        when(usuarioRepository.findByEmail("pedro@mail.com")).thenReturn(Optional.empty());
        when(usuarioRepository.save(user)).thenReturn(user);

        // Act
        var createdUser = usuarioService.CreateUser(user);

        // Assert
        assertNotNull(createdUser);
        assertEquals("Pedro", createdUser.getName());
        verify(usuarioRepository, times(1)).findByEmail("pedro@mail.com");
        verify(usuarioRepository, times(1)).save(user);
    }

    @Test
    void shouldThrowExceptionWhenEmailExists() {
        // Arrange
        Usuario user = new Usuario("1", "Ana", "ana@mail.com", "1234");
        when(usuarioRepository.findByEmail("ana@mail.com")).thenReturn(Optional.of(user));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> usuarioService.CreateUser(user));
        verify(usuarioRepository, times(1)).findByEmail("ana@mail.com");
    }

    @Test
    void shouldDeleteUserSuccessfully() {
        String userId = "user1";

        doNothing().when(usuarioRepository).deleteById(userId);

        usuarioService.DeleteUser(userId);

        verify(usuarioRepository, times(1)).deleteById(userId);
    }

    @Test
    void shouldUpdateUserSuccessfully() {
        String userId = "user1";
        Usuario existingUser = new Usuario(userId, "John Duran", "john@mail.escuelaing.edu.co", "1234");
        Usuario updatedUser = new Usuario(userId, "John Smith", "johnsmith@mail.escuelaing.edu.co", "abcd");

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario result = usuarioService.UpdateUser(userId, updatedUser);

        assertNotNull(result);
        assertEquals("John Smith", result.getName());
        assertEquals("johnsmith@mail.escuelaing.edu.co", result.getEmail());
        assertEquals("abcd", result.getPassword());

        verify(usuarioRepository, times(1)).findById(userId);
        verify(usuarioRepository, times(1)).save(existingUser);
    }
}
