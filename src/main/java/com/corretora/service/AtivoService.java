package com.corretora.service;

import com.corretora.dao.AtivoRepository;
import com.corretora.dto.recuperadorDTO.Ativo.AtivoDTO;
import com.corretora.excecao.AcaoInvalidaException;
import com.corretora.model.ativo.Ativo;
import com.corretora.service.strategyAtivoInformacoes.Recuperador;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtivoService {
    @Autowired
    private Recuperador recuperador;

    @Autowired
    private AtivoRepository ativoRepository;

    public AtivoDTO recuperarAtivo(String identificador) throws AcaoInvalidaException, JsonProcessingException {
        return recuperador.recuperarAtivo(identificador);
    }

    //public Ativo findAtivoByIdentificador()

    public Ativo saveAtivo(Ativo ativo){
        return ativoRepository.save(ativo);
    }


}
