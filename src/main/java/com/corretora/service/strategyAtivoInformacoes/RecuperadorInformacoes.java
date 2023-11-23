package com.corretora.service.strategyAtivoInformacoes;

import com.corretora.dto.recuperadorDTO.InformacoesDTO;
import com.corretora.excecao.AcaoInvalidaException;
import com.corretora.model.ativo.Ativo;

public interface RecuperadorInformacoes {
    public InformacoesDTO recuperarInformacoes(String identificador) throws AcaoInvalidaException;


}
