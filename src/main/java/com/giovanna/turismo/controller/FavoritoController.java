package com.giovanna.turismo.controller;

import com.giovanna.turismo.service.FavoritoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/favoritos")
public class FavoritoController {

    private final FavoritoService service;

    public FavoritoController(FavoritoService service) {
        this.service = service;
    }

    @PostMapping("/{pontoId}")
    public ResponseEntity<?> alternarFavorito(@PathVariable Long pontoId, Authentication auth) {
        service.alternarFavorito(auth.getName(), pontoId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> meusFavoritos(Authentication auth) {
        return ResponseEntity.ok(service.listarFavoritos(auth.getName()));
    }
}