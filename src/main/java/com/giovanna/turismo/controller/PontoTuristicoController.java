package com.giovanna.turismo.controller;

import com.giovanna.turismo.entity.PontoTuristico;
import com.giovanna.turismo.service.PontoTuristicoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.io.PrintWriter;
import java.io.StringWriter;

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

    @GetMapping("/export/csv")
    public ResponseEntity<String> exportarCsv() {
        List<PontoTuristico> pontos = service.listarTodos();

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        pw.println("ID;Nome;Cidade;Estado;Pais;MediaAvaliacoes");

        for (PontoTuristico p : pontos) {
            pw.printf("%d;%s;%s;%s;%s;%.2f%n",
                    p.getId(),
                    p.getNome(),
                    p.getCidade(),
                    p.getEstado() != null ? p.getEstado() : "",
                    p.getPais() != null ? p.getPais() : "",
                    p.getMediaAvaliacoes() != null ? p.getMediaAvaliacoes() : 0.0
            );
        }

        String csvContent = sw.toString();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"pontos.csv\"")
                .contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
                .body(csvContent);
    }
}