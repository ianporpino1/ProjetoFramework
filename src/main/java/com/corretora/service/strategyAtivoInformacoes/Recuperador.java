package com.corretora.service.strategyAtivoInformacoes;

import com.corretora.dto.recuperadorDTO.Ativo.AtivoDTO;
import com.corretora.dto.recuperadorDTO.Informacoes.InformacoesDTO;
import com.corretora.excecao.AcaoInvalidaException;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;


public interface Recuperador {
    AtivoDTO recuperarAtivo(String identificador) throws AcaoInvalidaException, JsonProcessingException;

    InformacoesDTO recuperarInformacoes(String identificador) throws AcaoInvalidaException, JsonProcessingException;


}
