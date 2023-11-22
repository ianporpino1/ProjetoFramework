package com.corretora.service.strategyRecomendacao;

import com.corretora.dto.apiResult.RecomendacaoDTO.Attributes;

import java.util.List;

public interface GeradorRecomendacao {

    public List<Double> gerarRecomendacao(Attributes attributes);
}
