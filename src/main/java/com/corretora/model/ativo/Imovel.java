package com.corretora.model.ativo;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;


@Entity
@DiscriminatorValue("imovel")
public class Imovel extends Ativo{


    public Imovel(String ticker, double preco) {
        super(ticker,preco);
    }

    public Imovel(double valor) {
    }

    public Imovel() {

    }
}
