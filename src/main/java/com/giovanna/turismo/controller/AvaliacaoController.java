package com.giovanna.turismo.controller;

import com.giovanna.turismo.entity.Avaliacao;
import com.giovanna.turismo.service.AvaliacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoService service;

    public AvaliacaoController(AvaliacaoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> criarOuAtualizar(@RequestBody Avaliacao req) {
        Avaliacao saved = service.criarOuAtualizar(req);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/ponto/{pontoId}")
    public ResponseEntity<List<Avaliacao>> listarPorPonto(@PathVariable Long pontoId) {
        return ResponseEntity.ok(service.listarPorPonto(pontoId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        service.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }
}