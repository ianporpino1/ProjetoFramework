package com.corretora.service.templateMethodImposto;

import com.corretora.dto.ResultadoDTO;
import com.corretora.model.Resultado;

import java.util.ArrayList;
import java.util.List;

public abstract class CalculadoraImposto {

    public double calcularImposto(double lucro, double volume){

        boolean isIsento = calcularIsencao(volume);

        if(!isIsento && lucro > 0) {
            return calcularValorImposto(lucro);
        }

        return 0;
    }



    public abstract boolean calcularIsencao(double volume);

    public abstract double calcularValorImposto(double total);

    protected Resultado calcularPrejuizoCompensar(){

        return null;
    }
}
