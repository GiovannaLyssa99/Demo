package com.giovanna.turismo.controller;

import com.giovanna.turismo.entity.PontoTuristico;
import com.giovanna.turismo.service.PontoTuristicoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pontos")
public class PontoTuristicoController {

    private final PontoTuristicoService service;

    public PontoTuristicoController(PontoTuristicoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PontoTuristico> criar(@RequestBody PontoTuristico p) {
        return ResponseEntity.ok(service.salvar(p));
    }

    @GetMapping
    public ResponseEntity<List<PontoTuristico>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PontoTuristico> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PontoTuristico> atualizar(@PathVariable Long id,
                                                    @RequestBody PontoTuristico p) {
        return ResponseEntity.ok(service.atualizar(id, p));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}