package com.corretora.service.strategyAtivoInformacoes;

import com.corretora.dto.recuperadorDTO.InformacoesDTO;
import com.corretora.excecao.AcaoInvalidaException;

public interface RecuperadorInformacoes {
    public InformacoesDTO recuperarInformacoes(String identificador) throws AcaoInvalidaException;
}
