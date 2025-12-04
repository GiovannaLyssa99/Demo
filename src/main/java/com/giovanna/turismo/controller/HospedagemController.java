package com.giovanna.turismo.controller;

import com.giovanna.turismo.entity.Hospedagem;
import com.giovanna.turismo.service.HospedagemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospedagens")
public class HospedagemController {

    private final HospedagemService service;

    public HospedagemController(HospedagemService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Hospedagem> criar(@RequestBody Hospedagem h) {
        return ResponseEntity.ok(service.criar(h));
    }

    @GetMapping("/ponto/{pontoId}")
    public ResponseEntity<List<Hospedagem>> listarPorPonto(@PathVariable Long pontoId) {
        return ResponseEntity.ok(service.listarPorPonto(pontoId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hospedagem> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hospedagem> atualizar(
            @PathVariable Long id,
            @RequestBody Hospedagem h
    ) {
        return ResponseEntity.ok(service.atualizar(id, h));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}