package com.giovanna.turismo.controller;

import com.giovanna.turismo.dto.JwtResponse;
import com.giovanna.turismo.dto.LoginRequest;
import com.giovanna.turismo.dto.RegisterRequest;
import com.giovanna.turismo.entity.Usuario;
import com.giovanna.turismo.security.JwtUtil;
import com.giovanna.turismo.service.AuthService;
import com.giovanna.turismo.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final UsuarioRepository usuarioRepository;

    public AuthController(AuthService authService, JwtUtil jwtUtil, UsuarioRepository usuarioRepository) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        Usuario u = authService.register(req);
        return ResponseEntity.ok(u);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {

        authService.authenticate(req.getEmail(), req.getSenha());

        String token = jwtUtil.generateToken(req.getEmail());

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@RequestHeader(value = "Authorization", required = false) String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Token ausente");
        }

        String token = authHeader.substring(7); // remove "Bearer "

        String email = jwtUtil.extractUsername(token);

        if (!jwtUtil.isTokenValid(token, email)) {
            return ResponseEntity.badRequest().body("Token inválido");
        }

        return usuarioRepository.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}