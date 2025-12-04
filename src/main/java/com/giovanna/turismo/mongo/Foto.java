package com.giovanna.turismo.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "fotos")
public class Foto {

    @Id
    private String id;

    private Long pontoId;
    private Long usuarioId;

    private String filename;
    private String titulo;
    private String path;
    private LocalDateTime createdAt;

    public Foto() {}

    public Foto(Long pontoId, Long usuarioId, String filename, String titulo, String path) {
        this.pontoId = pontoId;
        this.usuarioId = usuarioId;
        this.filename = filename;
        this.titulo = titulo;
        this.path = path;
        this.createdAt = LocalDateTime.now();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Long getPontoId() { return pontoId; }
    public void setPontoId(Long pontoId) { this.pontoId = pontoId; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public String getFilename() { return filename; }
    public void setFilename(String filename) { this.filename = filename; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}