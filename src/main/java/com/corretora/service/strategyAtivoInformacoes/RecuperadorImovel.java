package com.corretora.service.strategyAtivoInformacoes;

import com.corretora.dto.recuperadorDTO.Ativo.AtivoDTO;
import com.corretora.dto.recuperadorDTO.Ativo.ImovelDTO.ImovelDTO;
import com.corretora.dto.recuperadorDTO.Ativo.ListaImovelDTO;
import com.corretora.dto.recuperadorDTO.Ativo.ImovelDTO.Root;
import com.corretora.dto.recuperadorDTO.Ativo.ImovelDTO.RootLocation;
import com.corretora.dto.recuperadorDTO.Informacoes.InformacoesDTO;
import com.corretora.dto.recuperadorDTO.Informacoes.ListaInfoImovel;
import com.corretora.dto.recuperadorDTO.Informacoes.RecomendacaoImovelDTO.RootRecomendacao;
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
public class RecuperadorImovel implements Recuperador {

    @Value("${apiKey}")
    private String apiKey;

    @Override
    public AtivoDTO recuperarAtivo(String local) throws AcaoInvalidaException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-RapidAPI-Key", apiKey);
        headers.add("X-RapidAPI-Host","realtor26.p.rapidapi.com");

        HttpEntity<Object> entity= new HttpEntity<>(headers);

        String locationKey;
        try{
            ResponseEntity<String> response = restTemplate.exchange("https://realtor26.p.rapidapi.com/locations?location="  +local, HttpMethod.GET,entity,String.class);
            ObjectMapper om = new ObjectMapper();

            RootLocation root = om.readValue(response.getBody(), RootLocation.class);

            locationKey = root.data.get(0).key;

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        try {
            ResponseEntity<String> response = restTemplate.exchange("https://realtor26.p.rapidapi.com/properties/buy?locationKey="  + locationKey, HttpMethod.GET,entity,String.class);

            ObjectMapper om = new ObjectMapper();

            Root root = om.readValue(response.getBody(), Root.class);
            return new ListaImovelDTO(root.data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
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

            RootRecomendacao root = om.readValue(response.getBody(), RootRecomendacao.class);

            return new ListaInfoImovel(root.results.get(0).categories);

        }catch(HttpClientErrorException he){
            throw new AcaoInvalidaException("Codigo: " + identificador + " Nao é uma Acao Valida");
        }catch (UnrecognizedPropertyException upe){
            throw new AcaoInvalidaException("Ticker Obrigatorio");
        }

    }


}
