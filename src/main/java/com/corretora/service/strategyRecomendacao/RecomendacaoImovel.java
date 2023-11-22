package com.corretora.service.strategyRecomendacao;


import com.corretora.dto.apiResult.RecomendacaoDTO.Attributes;

import java.util.List;

public class RecomendacaoImovel implements GeradorRecomendacao {
    @Override
    public List<Double> geraRecomendacao(Attributes attributes) { //nota de 0 a 10 rankeando infraestrutura proxima
        return null;
    }
}
