package com.corretora.model;

import com.corretora.model.ativo.Ativo;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.annotation.Target;
import java.sql.Date;
import java.time.LocalDate;

@Entity(name = "Transacao")
public class Transacao implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Long idAtivo;

    private double preco;
    private int quantidade;

    private double totalTransacao;
    private TipoTransacao tipoTransacao;

    private Date data; //data da transacao

    private Long idUsuario;

    public Transacao(){}

    public double getTotal() {
        return totalTransacao;
    }

    public void setTotalTransacao(double totalTransacao) {
        this.totalTransacao = totalTransacao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
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

    public Long getIdAtivo() {
        return idAtivo;
    }

    public void setIdAtivo(Long idAtivo) {
        this.idAtivo = idAtivo;
    }
}
