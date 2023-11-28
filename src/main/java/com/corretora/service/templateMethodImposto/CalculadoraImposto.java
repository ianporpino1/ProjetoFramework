package com.corretora.service.templateMethodImposto;
import com.corretora.model.Imposto;

public abstract class CalculadoraImposto {

    public Imposto calcularImposto(double lucro, double volume){
        Imposto imposto = new Imposto();

        boolean isIsento = calcularIsencao(volume);

        if(!isIsento){
            if( lucro > 0) {
                imposto.setLucro(calcularValorImposto(lucro));
                return imposto;
            }
        }
        if(lucro < 0)
            calcularPrejuizoCompensar(imposto.getLucro());

        return new Imposto(0,0);
    }



    public abstract boolean calcularIsencao(double volume);

    public abstract double calcularValorImposto(double total);

    protected void calcularPrejuizoCompensar(double total){


    }
}
