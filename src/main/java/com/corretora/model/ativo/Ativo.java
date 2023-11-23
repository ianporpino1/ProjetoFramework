package com.corretora.model.ativo;

import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public abstract class Ativo implements Serializable {

    protected String identificador;
    protected double preco;

    public Ativo() {
    }
    
    public Ativo(double preco) {
        this.preco = preco;
    }
    
    public Ativo(String identificador, double preco) {
        this.identificador = identificador;
        this.preco = preco;
    }


    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public boolean isNomeNull() {
    	return identificador == null;
    }
}
