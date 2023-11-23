package com.corretora.service.templateMethodImposto;

import java.util.List;

public class CalculadoraImpostoImovel extends CalculadoraImposto{
    @Override
    public boolean calcularIsencao(double volume) {
        return false;
    }

    @Override
    public double calcularValorImposto(double total) {
        return 0;
    }


}
