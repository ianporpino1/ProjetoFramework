package com.corretora.model.ativo;

public class Acao extends Ativo{
    private String ticker;


    public Acao(String ticker, double price) {
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
}
