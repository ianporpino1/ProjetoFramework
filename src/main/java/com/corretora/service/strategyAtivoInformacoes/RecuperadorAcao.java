package com.corretora.service.strategyAtivoInformacoes;

import com.corretora.dto.recuperadorDTO.Ativo.AcaoDTO;
import com.corretora.dto.recuperadorDTO.Ativo.AtivoDTO;
import com.corretora.dto.recuperadorDTO.Informacoes.InformacoesDTO;
import com.corretora.dto.recuperadorDTO.Informacoes.RecomendacaoAcaoDTO.Root;
import com.corretora.excecao.AcaoInvalidaException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
@Service
@PropertySource("classpath:application-dev.properties")
public class RecuperadorAcao implements Recuperador {

    @Value("${apiKey}")
    private String apiKey;
    @Override
    public AtivoDTO recuperarAtivo(String ticker) throws AcaoInvalidaException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-RapidAPI-Key", apiKey);
        headers.add("X-RapidAPI-Host","realstonks.p.rapidapi.com");
        HttpEntity<Object> entity= new HttpEntity<>(headers);

        try{
            ResponseEntity<String> response = restTemplate.exchange("https://realstonks.p.rapidapi.com/"  +ticker, HttpMethod.GET,entity,String.class);

            ObjectMapper om = new ObjectMapper();

            AcaoDTO acao = om.readValue(response.getBody(), AcaoDTO.class);
            acao.ticker = ticker;
            
            return acao;

        }catch(HttpClientErrorException he){
            throw new AcaoInvalidaException("Codigo: " + ticker + " Nao é uma Acao Valida");
        }catch (JsonProcessingException upe){
            throw new AcaoInvalidaException("Ticker Obrigatorio");
        }
    }

    @Override
    public InformacoesDTO recuperarInformacoes(String identificador) throws AcaoInvalidaException, JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-RapidAPI-Key", apiKey);
        headers.add("X-RapidAPI-Host","seeking-alpha.p.rapidapi.com");

        HttpEntity<Object> entity= new HttpEntity<>(headers);

        try{
            ResponseEntity<String> response = restTemplate.exchange("https://seeking-alpha.p.rapidapi.com/symbols/get-valuation?symbols="  +identificador, HttpMethod.GET,entity,String.class);

            ObjectMapper om = new ObjectMapper();

            Root root = om.readValue(response.getBody(), Root.class);
            return root.data.get(0).attributes;

        }catch(HttpClientErrorException he){
            throw new AcaoInvalidaException("Codigo: " + identificador + " Nao é uma Acao Valida");
        }catch (UnrecognizedPropertyException upe){
            throw new AcaoInvalidaException("Ticker Obrigatorio");
        }

    }


}
