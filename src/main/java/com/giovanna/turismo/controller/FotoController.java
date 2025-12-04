package com.giovanna.turismo.controller;

import com.giovanna.turismo.mongo.Foto;
import com.giovanna.turismo.service.FotoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/pontos/{pontoId}/fotos")
public class FotoController {

    private final FotoService fotoService;

    public FotoController(FotoService fotoService) {
        this.fotoService = fotoService;
    }

    @PostMapping
    public ResponseEntity<?> upload(@PathVariable Long pontoId,
                                    @RequestParam(value = "usuarioId", required = false) Long usuarioId,
                                    @RequestParam("file") MultipartFile file,
                                    @RequestParam(value = "titulo", required = false) String titulo) throws Exception {
        Foto f = fotoService.salvarFoto(pontoId, usuarioId, file, titulo);
        return ResponseEntity.status(201).body(f);
    }
}