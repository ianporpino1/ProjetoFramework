package com.corretora.service;

import com.corretora.dto.recuperadorDTO.Informacoes.InformacoesDTO;
import com.corretora.dto.recuperadorDTO.Informacoes.RecomendacaoAcaoDTO.Attributes;
import com.corretora.excecao.AcaoInvalidaException;
import com.corretora.service.strategyAtivoInformacoes.Recuperador;
import com.corretora.service.strategyRecomendacao.GeradorRecomendacao;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecomendacaoService {
    @Autowired //acho q n funciona
    private GeradorRecomendacao geradorRecomendacao;

    @Autowired
    private Recuperador recuperador;

    @Autowired
    private AtivoService ativoService;
    public List<Double> processarInformacoes(Attributes attributes){

        return geradorRecomendacao.gerarRecomendacao(attributes);

    }

    public InformacoesDTO recuperarInformacoes(String identificador) throws AcaoInvalidaException, JsonProcessingException {
        return recuperador.recuperarInformacoes(identificador);
    }
}
