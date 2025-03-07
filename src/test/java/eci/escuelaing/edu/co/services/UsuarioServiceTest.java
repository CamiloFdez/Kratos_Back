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
    void shouldUpdateUser() {
        Usuario existingUser = new Usuario("1", "Juan", "juan@mail.com", "1234");
        Usuario updatedUser = new Usuario("1", "Juan Updated", "juan.updated@mail.com", "5678");
        when(usuarioRepository.findById("1")).thenReturn(Optional.of(existingUser));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(updatedUser);

        var result = usuarioService.UpdateUser("1", updatedUser);

        assertNotNull(result);
        assertEquals("Juan Updated", result.getName());
        assertEquals("juan.updated@mail.com", result.getEmail());
        verify(usuarioRepository, times(1)).findById("1");
        verify(usuarioRepository, times(1)).save(existingUser);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonexistentUser() {
        Usuario updatedUser = new Usuario("1", "Juan Updated", "juan.updated@mail.com", "5678");
        when(usuarioRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> usuarioService.UpdateUser("1", updatedUser));
        verify(usuarioRepository, times(1)).findById("1");
    }

    @Test
    void shouldDeleteUser() {
        doNothing().when(usuarioRepository).deleteById("1");

        assertDoesNotThrow(() -> usuarioService.DeleteUser("1"));
        verify(usuarioRepository, times(1)).deleteById("1");
    }
}
