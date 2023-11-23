package com.corretora.service.templateMethodImposto;
import com.corretora.model.Imposto;

public abstract class CalculadoraImposto {

    public Imposto calcularImposto(double lucro, double volume){
        Imposto imposto;

        boolean isIsento = calcularIsencao(volume);

        if(!isIsento){
            imposto = new Imposto();
            if( lucro > 0) {
                imposto.setLucro(calcularValorImposto(lucro));
                return imposto;
            }
            else if(lucro < 0){
                calcularPrejuizoCompensar(imposto.getLucro());
            }
        }


        return null;
    }



    public abstract boolean calcularIsencao(double volume);

    public abstract double calcularValorImposto(double total);

    protected void calcularPrejuizoCompensar(double total){


    }
}
