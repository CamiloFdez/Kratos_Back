package eci.escuelaing.edu.co.controllers;

import eci.escuelaing.edu.co.models.AuthenticationRequest;
import eci.escuelaing.edu.co.models.Usuario;
import eci.escuelaing.edu.co.repositories.UsuarioRepository;
import eci.escuelaing.edu.co.services.CustomUserDetailsService;
import eci.escuelaing.edu.co.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public String registerUser(@RequestBody Usuario usuario) {
        // Encode the user's password
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        // Save the user to the database
        userRepository.save(usuario);
        return "User registered successfully";
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return jwt;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }
}
