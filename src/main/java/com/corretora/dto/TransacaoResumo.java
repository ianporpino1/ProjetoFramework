package com.corretora.dto;

import com.corretora.model.Acao;
import com.corretora.model.TipoTransacao;
import com.corretora.model.Transacao;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

public class TransacaoResumo { //SOON TO BE DEPRECATED(TALVEZ N, USAR PARA FORMAR HISTORICO)
    private String ticker;
    private double preco;

    private double totalTransacao;
    private int quantidade;

    private String tipoTransacao;

    private String data;

    public TransacaoResumo(String ticker, int quantidade, double preco, double total, String tipoTransacao, String data) {
        this.ticker = ticker;
        this.preco = preco;
        this.quantidade = quantidade;
        this.totalTransacao = total;
        this.tipoTransacao = tipoTransacao;
        this.data = data;
    }

    public TransacaoResumo() {
    }

    public String getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(String tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
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


    public double getTotal() {
        return totalTransacao;
    }

    public void setTotal(double total) {
        this.totalTransacao = total;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
