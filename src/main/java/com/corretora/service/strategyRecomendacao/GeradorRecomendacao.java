package com.corretora.service.strategyRecomendacao;

import com.corretora.dto.recuperadorDTO.Informacoes.RecomendacaoAcaoDTO.Attributes;

import java.util.List;

public interface GeradorRecomendacao {

    public List<Double> gerarRecomendacao(Attributes attributes);
}
