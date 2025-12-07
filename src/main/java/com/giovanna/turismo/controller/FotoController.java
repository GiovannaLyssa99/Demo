package com.giovanna.turismo.controller;

import com.giovanna.turismo.mongo.Foto;
import com.giovanna.turismo.service.FotoService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLConnection;
import java.util.List;

@RestController
public class FotoController {

    private final FotoService fotoService;

    public FotoController(FotoService fotoService) {
        this.fotoService = fotoService;
    }

    @PostMapping("/api/pontos/{pontoId}/fotos")
    public ResponseEntity<?> upload(@PathVariable Long pontoId,
                                    @RequestParam(value = "usuarioId", required = false) Long usuarioId,
                                    @RequestParam("file") MultipartFile file,
                                    @RequestParam(value = "titulo", required = false) String titulo) throws Exception {
        Foto f = fotoService.salvarFoto(pontoId, usuarioId, file, titulo);
        return ResponseEntity.status(201).body(f);
    }

    @GetMapping("/api/pontos/{pontoId}/fotos")
    public ResponseEntity<List<Foto>> listar(@PathVariable Long pontoId) {
        return ResponseEntity.ok(fotoService.listarPorPonto(pontoId));
    }

    @GetMapping("/api/fotos/{id}")
    public ResponseEntity<byte[]> verFoto(@PathVariable String id) {
        try {
            Foto foto = fotoService.buscarPorId(id);
            byte[] imagem = fotoService.recuperarImagem(foto.getFilename());
            
            String mimeType = URLConnection.guessContentTypeFromName(foto.getFilename());
            if (mimeType == null) mimeType = "image/jpeg";

            return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType)).body(imagem);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}