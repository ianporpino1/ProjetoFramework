package com.corretora.service.strategyAtivoInformacoes;

import com.corretora.dto.apiResult.AcaoDTO;
import com.corretora.dto.recuperadorDTO.InformacoesDTO;
import com.corretora.dto.recuperadorDTO.AtivoESPECIFICODTO;
import com.corretora.excecao.AcaoInvalidaException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class RecuperadorAcao implements RecuperadorInformacoes{

    @Value("${apiKey}")
    private String apiKey;
    @Override
    public InformacoesDTO recuperarInformacoes(String ticker) throws AcaoInvalidaException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-RapidAPI-Key", apiKey);
        headers.add("X-RapidAPI-Host","realstonks.p.rapidapi.com");
        HttpEntity<Object> entity= new HttpEntity<>(headers);

        try{
            ResponseEntity<String> response = restTemplate.exchange("https://realstonks.p.rapidapi.com/"  +ticker, HttpMethod.GET,entity,String.class);

            ObjectMapper om = new ObjectMapper();

            InformacoesDTO acao = om.readValue(response.getBody(), InformacoesDTO.class);
            acao.identificador = ticker;
            return acao;

        }catch(HttpClientErrorException he){
            throw new AcaoInvalidaException("Codigo: " + ticker + " Nao é uma Acao Valida");
        }catch (JsonProcessingException upe){
            throw new AcaoInvalidaException("Ticker Obrigatorio");
        }
    }
}
