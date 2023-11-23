package com.corretora.service.templateMethodImposto;

import com.corretora.service.templateMethodImposto.prejuizoCompensarInterface.PrejuizoCompensarAcaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculadoraImpostoAcao extends CalculadoraImposto{

    private final double ALIQUOTA_IMPOSTO_ACOES_BR = 0.15;

    private final double VALOR_ISENCAO_ACOES_BRASIL = 20000;

    @Autowired
    PrejuizoCompensarAcaoService prejuizoCompensarAcaoService;


    @Override
    public boolean calcularIsencao(double volume) {
        if(volume <= VALOR_ISENCAO_ACOES_BRASIL)
            return true;
        else if(volume > VALOR_ISENCAO_ACOES_BRASIL){
            return false;
        }
        return false;
    }

    @Override
    public double calcularValorImposto(double total) {
        if(total > 0){
            double novoImposto = total * ALIQUOTA_IMPOSTO_ACOES_BR;
            novoImposto = novoImposto*100;
            novoImposto = Math.round(novoImposto);
            novoImposto = novoImposto/100;
            return novoImposto;
        }
        else{
            return 0;
        }
    }


    @Override
    public void calcularPrejuizoCompensar(double total){

        prejuizoCompensarAcaoService.setPrejuizoCompensar(total);
    }




}
