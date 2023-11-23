package com.corretora.service.strategyAtivoInformacoes;

import com.corretora.dto.apiResult.RecomendacaoDTO.Root;
import com.corretora.dto.recuperadorDTO.InformacoesDTO;
import com.corretora.excecao.AcaoInvalidaException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpHeaders;

@Service
@PropertySource("classpath:application-dev.properties")
public class RecuperadorAcaoValuation implements RecuperadorInformacoes{

    @Value("${apiKey}")
    private String apiKey;
    @Override
    public InformacoesDTO recuperarInformacoes(String identificador) throws AcaoInvalidaException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-RapidAPI-Key", apiKey);
        headers.add("X-RapidAPI-Host","seeking-alpha.p.rapidapi.com");

        HttpEntity<Object> entity= new HttpEntity<>(headers);

        try{
            ResponseEntity<String> response = restTemplate.exchange("https://seeking-alpha.p.rapidapi.com/symbols/get-valuation?symbols="  +ticker, HttpMethod.GET,entity,String.class);

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
