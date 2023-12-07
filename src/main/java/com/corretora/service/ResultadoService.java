package com.corretora.service;


import com.corretora.dao.ResultadoRepository;
import com.corretora.dto.ResultadoDTO;
import com.corretora.model.Imposto;
import com.corretora.model.Resultado;
import com.corretora.service.templateMethodImposto.CalculadoraImposto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResultadoService {

    @Autowired
    private ResultadoRepository resultadoRepository;

    @Autowired
    private AutorizacaoService autorizacaoService;

    @Autowired
    private CalculadoraImposto calculadoraImposto;

    private Imposto imposto;

    public void saveResultado(Resultado resultado){
        this.resultadoRepository.save(resultado);
    }

    public List<ResultadoDTO> findAllResultadoByData(int mes, int ano){
        List<Object[]> objResultados = this.resultadoRepository.findAllResultadoByData(mes,ano,autorizacaoService.LoadUsuarioLogado().getId());
        return formatResultado(objResultados);
    }

    public Resultado findResultadoByName(String ativo){
        return this.resultadoRepository.findResultadoByName(autorizacaoService.LoadUsuarioLogado().getId(),ativo);
    }

    public List<ResultadoDTO> formatResultado(List<Object[]> objResultados){
        List<ResultadoDTO> resultadosList = new ArrayList<>();
        for (Object[] objResultado : objResultados) {
            String ativo = (String) objResultado[0];
            double volume = (double) objResultado[1];
            double resultado = (double) objResultado[2];
            double resultadoPorcentegem = (double) objResultado[3];


            ResultadoDTO res = new ResultadoDTO(ativo, volume, resultado, resultadoPorcentegem);

            resultadosList.add(res);
        }
        return resultadosList;
    }

    public Imposto calcularIR(int mes, int ano) {
        imposto= new Imposto();
        List<ResultadoDTO> resultadoDTOList = findAllResultadoByData(mes,ano);
        calcularResultadoTotal(resultadoDTOList);

        imposto = calculadoraImposto.calcularImposto(imposto.getLucro(),imposto.getVolume());

        return imposto;
    }


    public void calcularResultadoTotal(List<ResultadoDTO> resultadoDTOList) {
        double lucro = 0;
        double volume =0;
        for(ResultadoDTO resultado : resultadoDTOList){
            lucro += resultado.getResultado();
            volume+= -(resultado.getVolume());
        }
        lucro = lucro*100;
        lucro = Math.round(lucro);
        imposto.setLucro(lucro/100);

        volume = volume*100;
        volume = Math.round(volume);
        imposto.setVolume(volume/100);
    }

    public Imposto getImposto() {
        return imposto;
    }

    public void setImposto(Imposto imposto) {
        this.imposto = imposto;
    }
}