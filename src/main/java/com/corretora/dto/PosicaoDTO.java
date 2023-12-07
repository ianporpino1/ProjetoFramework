package com.corretora.dto;


public class PosicaoDTO {
    private String ticker;

    private double precoMedio;

    private double valorTotal;
    private int quantidadeTotal;

    public  PosicaoDTO(){};

    public PosicaoDTO(String ticker, int quantidadeTotal, double precoMedio, double valorTotal) {
        this.ticker =ticker;
        this.quantidadeTotal = quantidadeTotal;
        this.precoMedio = precoMedio;
        this.valorTotal = valorTotal;
    }

    public PosicaoDTO(int quantidadeTotal, double precoMedio, double valorTotal) {
    }


    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
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

    public int getQuantidadeTotal() {
        return quantidadeTotal;
    }

    public void setQuantidadeTotal(int quantidadeTotal) {
        this.quantidadeTotal = quantidadeTotal;
    }
}