package com.corretora.service.strategyRecomendacao;

import com.corretora.dto.apiResult.RecomendacaoDTO.Attributes;

import java.util.List;

public class RecomendacaoVeiculo implements GeradorRecomendacao{
    @Override
    public List<Double> geraRecomendacao(Attributes attributes) { //Porcentagem de depreciacao e custo de manutencao
        return null;
    }
}
