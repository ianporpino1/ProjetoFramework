package com.corretora.model;

import com.corretora.model.ativo.Ativo;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity(name = "Posicao")
public class Posicao implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idAtivo;

    private int quantidadeTotal;
    private double precoMedio;
    private double valorTotal;
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

    public double getPrecoMedio() {
        return precoMedio;
    }

    public void setPrecoMedio(double precoMedio) {
        this.precoMedio = precoMedio;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public StatusPosicao getStatusPosicao() {
        return statusPosicao;
    }

    public void setStatusPosicao() {
        if(this.getQuantidadeTotal() == 0){
            this.statusPosicao= StatusPosicao.FECHADA;
        }
        else if(this.getQuantidadeTotal() >0){
            this.statusPosicao= StatusPosicao.COMPRADA;
        }
        else{
            this.statusPosicao= StatusPosicao.VENDIDA;
        }
    }

    public int getQuantidadeTotal() {
        return quantidadeTotal;
    }

    public void setQuantidadeTotal(int quantidade) {
        this.quantidadeTotal = quantidade;
    }

    public Long getIdAtivo() {
        return idAtivo;
    }

    public void setIdAtivo(Long idAtivo) {
        this.idAtivo = idAtivo;
    }

}
