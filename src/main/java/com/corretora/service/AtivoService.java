package com.corretora.service;

import com.corretora.model.ativo.Ativo;
import com.corretora.service.strategyAtivoInformacoes.RecuperadorInformacoes;

public class AtivoService {

    RecuperadorInformacoes recuperadorInformacoes;

    Ativo ativo;


    public Ativo recuperarAtivo(String identificador){
        //retorno sera algum DTO = recuperadorInformacoes.recuperarInformacoes();
        //transformar DTO em Ativo
        //return informacoes;
        return ativo;
    }
}
