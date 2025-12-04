package com.giovanna.turismo.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "hospedagens", indexes = {
        @Index(columnList = "ponto_id", name = "idx_hospedagem_ponto")
})
public class Hospedagem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ponto_id", nullable = false)
    private Long pontoId;

    @Column(nullable = false)
    private String nome;

    private String endereco;

    private String telefone;

    @Column(name = "preco_medio")
    private Double precoMedio;

    private String tipo;

    @Column(name = "link_reserva")
    private String linkReserva;

    public Hospedagem() {}

    public Hospedagem(Long id, Long pontoId, String nome, String endereco, String telefone,
                      Double precoMedio, String tipo, String linkReserva) {
        this.id = id;
        this.pontoId = pontoId;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.precoMedio = precoMedio;
        this.tipo = tipo;
        this.linkReserva = linkReserva;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPontoId() {
        return pontoId;
    }

    public void setPontoId(Long pontoId) {
        this.pontoId = pontoId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Double getPrecoMedio() {
        return precoMedio;
    }

    public void setPrecoMedio(Double precoMedio) {
        this.precoMedio = precoMedio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLinkReserva() {
        return linkReserva;
    }

    public void setLinkReserva(String linkReserva) {
        this.linkReserva = linkReserva;
    }

    @Override
    public String toString() {
        return "Hospedagem{" +
                "id=" + id +
                ", pontoId=" + pontoId +
                ", nome='" + nome + '\'' +
                ", endereco='" + endereco + '\'' +
                ", telefone='" + telefone + '\'' +
                ", precoMedio=" + precoMedio +
                ", tipo='" + tipo + '\'' +
                ", linkReserva='" + linkReserva + '\'' +
                '}';
    }
}