package com.giovanna.turismo.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "comentarios")
public class Comentario {

    @Id
    private String id;

    private Long pontoId;

    private Long usuarioId;

    private String texto;

    private LocalDateTime createdAt;

    private List<Resposta> respostas = new ArrayList<>();

    public Comentario() {
        this.respostas = new ArrayList<>();
    }

    public Comentario(Long pontoId, Long usuarioId, String texto) {
        this.pontoId = pontoId;
        this.usuarioId = usuarioId;
        this.texto = texto;
        this.createdAt = LocalDateTime.now();
        this.respostas = new ArrayList<>();
    }

    public Comentario(String id, Long pontoId, Long usuarioId, String texto,
                      LocalDateTime createdAt, List<Resposta> respostas) {
        this.id = id;
        this.pontoId = pontoId;
        this.usuarioId = usuarioId;
        this.texto = texto;
        this.createdAt = createdAt;
        this.respostas = respostas != null ? respostas : new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getPontoId() {
        return pontoId;
    }

    public void setPontoId(Long pontoId) {
        this.pontoId = pontoId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Resposta> getRespostas() {
        return respostas;
    }

    public void setRespostas(List<Resposta> respostas) {
        this.respostas = respostas != null ? respostas : new ArrayList<>();
    }

    // ============================
    // MÉTODOS AUXILIARES
    // ============================

    public void addResposta(Resposta r) {
        if (this.respostas == null) {
            this.respostas = new ArrayList<>();
        }
        this.respostas.add(r);
    }
}