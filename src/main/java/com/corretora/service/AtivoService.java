package com.corretora.service;

import com.corretora.dto.recuperadorDTO.InformacoesDTO;
import com.corretora.model.ativo.Ativo;
import com.corretora.service.strategyAtivoInformacoes.RecuperadorInformacoes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AtivoService {
    @Autowired
    protected RecuperadorInformacoes recuperadorInformacoes;

    private Ativo ativo;


    public Ativo recuperarAtivo(String identificador){
        //InformacoesDTO informacoes= recuperadorInformacoes.recuperarInformacoes();
        //transformar DTO em Ativo
        return null;
    }


}
