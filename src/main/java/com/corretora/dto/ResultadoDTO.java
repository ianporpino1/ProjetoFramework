package com.corretora.dto;


import java.sql.Date;

public class ResultadoDTO {

    private String ativo;

    private double resultado;

    private double resultadoPorcentagem;

    private double volume;


    public ResultadoDTO(String ativo,double volume, double resultado, double resultadoPorcentagem) {
        this.ativo = ativo;
        this.volume = volume;
        this.resultado = resultado;
        this.resultadoPorcentagem = resultadoPorcentagem;

    }

    public ResultadoDTO(){};

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getResultado() {
        return resultado;
    }

    public void setResultado(double resultado) {
        this.resultado = resultado;
    }

    public double getResultadoPorcentagem() {
        return resultadoPorcentagem;
    }

    public void setResultadoPorcentagem(double resultadoPorcentagem) {
        this.resultadoPorcentagem = resultadoPorcentagem;
    }

}