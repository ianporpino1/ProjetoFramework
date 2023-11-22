package com.corretora.model;

import com.corretora.model.ativo.Ativo;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity(name = "Posicao")
public class Posicao implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Ativo ativo;

    private StatusPosicao statusPosicao;

    private Long idUsuario;

    public Posicao() {
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ativo getAtivo() {
        return ativo;
    }

    public void setAtivo(Ativo acao) {
        this.ativo = ativo;
    }

    public StatusPosicao getStatusPosicao() {
        return statusPosicao;
    }

    public void setStatusPosicao(StatusPosicao statusPosicao) {
        this.statusPosicao = statusPosicao;
    }




}
