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

    public Usuario CreateUser(Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new IllegalArgumentException("The email " + usuario.getEmail() +
                    " already exists");
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario UpdateUser(String id, Usuario usuarioActualizado) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setName(usuarioActualizado.getName());
            usuario.setEmail(usuarioActualizado.getEmail());
            usuario.setPassword(usuarioActualizado.getPassword());
            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public void DeleteUser(String id) { usuarioRepository.deleteById(id); }
}
