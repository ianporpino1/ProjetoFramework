package com.corretora.model;

import com.corretora.model.ativo.Ativo;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

@Entity(name = "Transacao")
public class Transacao implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @Nullable//
    private Ativo ativo;

    private int quantidade;

    private double totalTransacao;
    private TipoTransacao tipoTransacao;

    private Date data; //data da transacao

    private Long idUsuario;

    public Transacao(){}


    public Ativo getAtivo() {
        return ativo;
    }

    public void setAtivo(Ativo ativo) {
        this.ativo = ativo;
    }

    public double getTotal() {
        return totalTransacao;
    }

    public void setTotalTransacao(double totalTransacao) {
        this.totalTransacao = totalTransacao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public TipoTransacao getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(TipoTransacao tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setTodayData() {
        this.data = Date.valueOf(LocalDate.now());
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
}
