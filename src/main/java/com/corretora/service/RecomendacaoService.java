package com.corretora.service;

import com.corretora.dto.apiResult.RecomendacaoDTO.Attributes;
import com.corretora.excecao.AcaoInvalidaException;
import com.corretora.service.strategyRecomendacao.GeradorRecomendacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecomendacaoService {
    @Autowired //acho q n funciona
    private GeradorRecomendacao geradorRecomendacao;

    @Autowired
    private AtivoService ativoService;
    public List<Double> processarInformacoes(Attributes attributes){

        return geradorRecomendacao.gerarRecomendacao(attributes);

    }

    public void recuperarInformacoes(String identificador) throws AcaoInvalidaException {
        ativoService.recuperadorInformacoes.recuperarInformacoes(identificador);
    }
}
