package com.giovanna.turismo.controller;

import com.giovanna.turismo.mongo.Comentario;
import com.giovanna.turismo.mongo.ComentarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/comentarios")
public class ComentarioController {

    private final ComentarioRepository repo;

    public ComentarioController(ComentarioRepository repo) {
        this.repo = repo;
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
}