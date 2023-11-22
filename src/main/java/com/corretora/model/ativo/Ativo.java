package com.corretora.model.ativo;

import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class Ativo implements Serializable {

    protected String nome;
    protected double preco;

    protected String identificador;

    public Ativo() {
    }
    
    public Ativo(double preco) {
        this.preco = preco;
    }
    
    public Ativo(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }


    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isNomeNull() {
    	return nome == null;
    }
}
