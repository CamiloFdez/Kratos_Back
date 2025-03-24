package eci.escuelaing.edu.co.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UsuarioTest {

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario("1", "Juan Pérez", "juan@mail.escuelaing.edu.co", "contraseña123", "ADMIN");
    }

    @Test
    void shouldCreateUsuarioCorrectly() {
        assertThat(usuario.getId()).isEqualTo("1");
        assertThat(usuario.getName()).isEqualTo("Juan Pérez");
        assertThat(usuario.getEmail()).isEqualTo("juan@mail.escuelaing.edu.co");
        assertThat(usuario.getPassword()).isEqualTo("contraseña123");
    }

    @Test
    void shouldModifyUsuarioCorrectly() {
        usuario.setId("2");
        usuario.setName("Ana Gómez");
        usuario.setEmail("ana@mail.escuelaing.edu.co");
        usuario.setPassword("anaContraseña456");

        assertThat(usuario.getId()).isEqualTo("2");
        assertThat(usuario.getName()).isEqualTo("Ana Gómez");
        assertThat(usuario.getEmail()).isEqualTo("ana@mail.escuelaing.edu.co");
        assertThat(usuario.getPassword()).isEqualTo("anaContraseña456");
    }

    @Test
    void shouldHandleNullValues() {
        usuario.setName(null);
        usuario.setEmail(null);
        usuario.setPassword(null);

        assertThat(usuario.getName()).isNull();
        assertThat(usuario.getEmail()).isNull();
        assertThat(usuario.getPassword()).isNull();
    }
}
