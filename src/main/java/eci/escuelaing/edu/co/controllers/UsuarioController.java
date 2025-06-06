package eci.escuelaing.edu.co.controllers;

import eci.escuelaing.edu.co.models.Usuario;
import eci.escuelaing.edu.co.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    // Obtain all users
    @GetMapping
    public List<Usuario> listUsers() { return usuarioService.ObtainAllUsers(); }

    // Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtainUserById(@PathVariable String id) {
        Optional<Usuario> usuario = usuarioService.ObtainUserById(id);
        return usuario.map(ResponseEntity::ok).orElseGet(()
                -> ResponseEntity.notFound().build());
    }

    // Crear nuevo usuario
    @PostMapping
    public ResponseEntity<Usuario> addUser(@RequestBody Usuario usuario) {
        try {
            Usuario newUser = usuarioService.CreateUser(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUser(@PathVariable String id,
                                              @RequestBody Usuario usuario) {
        try {
            Usuario updatedUser = usuarioService.UpdateUser(id, usuario);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        usuarioService.DeleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Usuario usuario) {
        Optional<Usuario> usuarioExistente = usuarioService.ObtainUserByEmail(usuario.getEmail());

        if (usuarioExistente.isPresent() &&
                usuarioExistente.get().getPassword().equals(usuario.getPassword())) {
            Map<String, Object> response = new HashMap<>();
            response.put("id", usuarioExistente.get().getId());
            response.put("email", usuarioExistente.get().getEmail());
            response.put("nombre", usuarioExistente.get().getName());
            response.put("rol", usuarioExistente.get().getRol());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
}
