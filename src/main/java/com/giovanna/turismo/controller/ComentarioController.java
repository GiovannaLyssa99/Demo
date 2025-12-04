package com.giovanna.turismo.controller;

import com.giovanna.turismo.mongo.Comentario;
import com.giovanna.turismo.mongo.ComentarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.giovanna.turismo.repository.UsuarioRepository;
import com.giovanna.turismo.entity.Usuario;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/comentarios")
public class ComentarioController {

    private final ComentarioRepository repo;
    private final UsuarioRepository usuarioRepo;

    public ComentarioController(ComentarioRepository repo, UsuarioRepository usuarioRepo) {
        this.repo = repo;
        this.usuarioRepo = usuarioRepo;
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Comentario c) {
        if (c.getTexto() == null || c.getTexto().isBlank()) {
            throw new IllegalArgumentException("Comentário não pode ser vazio");
        }
        if (c.getTexto().length() > 500) {
            throw new IllegalArgumentException("Comentário deve ter no máximo 500 caracteres");
        }
        c.setCreatedAt(LocalDateTime.now());
        Comentario saved = repo.save(c);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/ponto/{pontoId}")
    public ResponseEntity<List<Comentario>> listarPorPonto(@PathVariable Long pontoId) {
        return ResponseEntity.ok(repo.findByPontoIdOrderByCreatedAtDesc(pontoId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable String id, Authentication auth) {
        Comentario c = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comentário não encontrado"));

        String email = auth.getName();
        boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

        if (!isAdmin) {
            Usuario u = usuarioRepo.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("Usuário inválido"));
            
            if (!c.getUsuarioId().equals(u.getId())) {
                return ResponseEntity.status(403).body("Sem permissão para excluir este comentário");
            }
        }

        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}