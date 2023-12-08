package com.corretora.service.strategyRecomendacao;

import com.corretora.dto.recuperadorDTO.Informacoes.InformacoesDTO;

import java.util.List;

public interface GeradorRecomendacao {

    public List<Double> gerarRecomendacao(InformacoesDTO informacoesDTO);
}
