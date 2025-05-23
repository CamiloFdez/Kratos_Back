package eci.escuelaing.edu.co.services;
import eci.escuelaing.edu.co.models.Usuario;
import eci.escuelaing.edu.co.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> ObtainAllUsers() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> ObtainUserById(String id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> ObtainUserByEmail(String email) { return usuarioRepository.findByEmail(email); }

    public Usuario CreateUser(Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new IllegalArgumentException("The email " + usuario.getEmail() +
                    " already exists");
        }

        if (usuario.getRol() == null || usuario.getRol().isEmpty()) {
            usuario.setRol("PROFESOR");
        } else if (!usuario.getRol().equals("ADMIN") && !usuario.getRol().equals("PROFESOR")) {
            throw new IllegalArgumentException("Rol inválido");
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario UpdateUser(String id, Usuario usuarioActualizado) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setName(usuarioActualizado.getName());
            usuario.setEmail(usuarioActualizado.getEmail());
            usuario.setPassword(usuarioActualizado.getPassword());
            usuario.setRol(usuarioActualizado.getRol());
            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public void DeleteUser(String id) { usuarioRepository.deleteById(id); }


}

