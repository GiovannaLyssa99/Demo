package com.giovanna.turismo.service;

import com.giovanna.turismo.dto.RegisterRequest;
import com.giovanna.turismo.entity.Usuario;
import com.giovanna.turismo.repository.UsuarioRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(UsuarioRepository usuarioRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }


    public Usuario register(RegisterRequest req) {
        if (usuarioRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("E-mail já cadastrado");
        }

        Usuario u = new Usuario();
        u.setLogin(req.getLogin());
        u.setEmail(req.getEmail());
        u.setNome(req.getNome());
        u.setSenhaHash(passwordEncoder.encode(req.getSenha()));

        u.setRole("ROLE_USER"); 

        return usuarioRepository.save(u);
    }

    public void authenticate(String email, String senha) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, senha)
        );
    }
}