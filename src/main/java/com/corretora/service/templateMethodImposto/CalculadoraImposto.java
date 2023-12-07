package com.corretora.service.templateMethodImposto;
import com.corretora.model.Imposto;

public abstract class CalculadoraImposto {

    public Imposto calcularImposto(double lucro, double volume){
        Imposto imposto = new Imposto();

        boolean isIsento = calcularIsencao(volume);

        if(!isIsento){
            if( lucro > 0) {
                imposto.setValorImposto(calcularValorImposto(lucro));
                imposto.setLucro(lucro);
                imposto.setVolume(volume);
                return imposto;
            }
        }
        if(lucro < 0)
            calcularPrejuizoCompensar(lucro);

        return new Imposto(0,lucro);
    }



    public abstract boolean calcularIsencao(double volume);

    public abstract double calcularValorImposto(double total);

    protected void calcularPrejuizoCompensar(double total){


    }
}
