package com.giovanna.turismo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "avaliacoes",
       indexes = {
         @Index(columnList = "ponto_id", name = "idx_avaliacao_ponto"),
         @Index(columnList = "usuario_id", name = "idx_avaliacao_usuario")
       },
       uniqueConstraints = {
         @UniqueConstraint(columnNames = {"ponto_id", "usuario_id"}, name = "uk_ponto_usuario_avaliacao")
       }
)
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ponto_id", nullable = false)
    private Long pontoId;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(nullable = false)
    private Integer nota; // 1..5

    @Column(columnDefinition = "text")
    private String comentario;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Avaliacao() {
        this.createdAt = LocalDateTime.now();
    }


    public Long getId() {
        return id;
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

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}