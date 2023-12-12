package com.corretora.service.templateMethodImposto;

import org.springframework.stereotype.Service;

@Service
public class CalculadoraImpostoImovel extends CalculadoraImposto {

    private final double ALIQUOTA_IMPOSTO_IMOVEIS_BR_1 = 0.15;
    private final double ALIQUOTA_IMPOSTO_IMOVEIS_BR_2 = 0.175;
    private final double ALIQUOTA_IMPOSTO_IMOVEIS_BR_3 = 0.2;
    private final double ALIQUOTA_IMPOSTO_IMOVEIS_BR_4 = 0.225;

    private final double VALOR_ISENCAO_IMOVEIS_BRASIL = 35000;

    @Override
    public boolean calcularIsencao(double volume) {
        if(volume <= VALOR_ISENCAO_IMOVEIS_BRASIL)
            return true;
        else if(volume > VALOR_ISENCAO_IMOVEIS_BRASIL){
            return false;
        }
        return false;
    }

    @Override
    public double calcularValorImposto(double total) {
        if(total > 0){
            double novoImposto = 0;
            if(total <= 5000000){
                novoImposto = total * ALIQUOTA_IMPOSTO_IMOVEIS_BR_1;
            }
            else if(total > 50000000 && total <= 100000000){
                novoImposto = total * ALIQUOTA_IMPOSTO_IMOVEIS_BR_2;
            }
            else if(total > 100000000 && total <= 300000000){
                novoImposto = total * ALIQUOTA_IMPOSTO_IMOVEIS_BR_3;
            }
            else if(total >300000000){
                novoImposto = total * ALIQUOTA_IMPOSTO_IMOVEIS_BR_4;
            }

            novoImposto = novoImposto*100;
            novoImposto = Math.round(novoImposto);
            novoImposto = novoImposto/100;
            return novoImposto;
        }
        else{
            return 0;
        }
    }
}
