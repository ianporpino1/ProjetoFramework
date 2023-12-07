package com.corretora.model.ativo;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import org.springframework.stereotype.Component;


@Entity
@DiscriminatorValue("acao")
public class Acao extends Ativo{

    String a;
    public Acao(String ticker, double preco) {
        super(ticker,preco);
    }

    public Acao(double valor) {
    }

    public Acao() {

    }
}
