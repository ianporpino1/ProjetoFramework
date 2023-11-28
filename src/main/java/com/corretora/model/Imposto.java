package com.corretora.model;

import java.time.LocalDate;

public class Imposto {

    private double valorImposto;

    private double volume;

    private double lucro;




    public Imposto() {

    }

    public Imposto(double valorImposto, double lucro) {
        this.valorImposto =valorImposto;
        this.lucro=lucro;
    }

    public double getValorImposto() {
        return valorImposto;
    }

    public void setValorImposto(double valorImposto) {
        this.valorImposto = valorImposto;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getLucro() {
        return lucro;
    }

    public void setLucro(double lucro) {
        this.lucro = lucro;
    }


}
