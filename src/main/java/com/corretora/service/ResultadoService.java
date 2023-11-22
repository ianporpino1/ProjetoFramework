package com.corretora.service;


import com.corretora.dao.ResultadoRepository;
import com.corretora.dto.ResultadoDTO;
import com.corretora.model.Resultado;
import com.corretora.service.templateMethodImposto.CalculadoraImposto;
import com.corretora.service.templateMethodImposto.CalculadoraImpostoAcao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ResultadoService {

    @Autowired
    private ResultadoRepository resultadoRepository;

    @Autowired
    private AutorizacaoService autorizacaoService;

    @Autowired
    private CalculadoraImposto calculadoraImposto;


    private double imposto;

    private double volume;

    private double lucro;

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

    //FLEXIVEL
    public double calcularIR(List<ResultadoDTO> resultadoDTOList) {


        imposto = calculadoraImposto.calcularImposto(lucro,volume);


//        Resultado prejuizo = findResultadoByName("prejuizoCompensar");
//        if(total > 0){
//            novoImposto = total * ALIQUOTA_IR_BRASIL;
//            if(prejuizo != null)
//                atualizarPrejuizoCompensar(prejuizo,total);
//        }
//        else if(total < 0){
//            setPrejuizoCompensar(prejuizo,total);
//        }

        return imposto;
    }


    //opcional(somente para acoes)
    public void setPrejuizoCompensar(Resultado prejuizo, double total){
        if(prejuizo==null){
            Resultado prejuizoCompensar = new Resultado();
            prejuizoCompensar.setAtivo("prejuizoCompensar");
            prejuizoCompensar.setResultado(total);
            prejuizoCompensar.setIdUsuario(autorizacaoService.LoadUsuarioLogado().getId());
            prejuizoCompensar.setData(Date.valueOf(LocalDate.now().plusMonths(1)));
            saveResultado(prejuizoCompensar);

        }
        else{
            atualizarPrejuizoCompensar(prejuizo,total);
        }
    }

    //opcional(somente para acoes)
    public void atualizarPrejuizoCompensar(Resultado prejuizo, double total){
        if(total > 0)
            prejuizo.setResultado(0);
        else
            prejuizo.setResultado(total);
        saveResultado(prejuizo);
    }


    public void calcularResultadoTotal(List<ResultadoDTO> resultadoDTOList) {
        lucro=0;
        volume =0;
        for(ResultadoDTO resultado : resultadoDTOList){
            lucro += resultado.getResultado();
            volume+= resultado.getVolume();
        }
        lucro = lucro*100;
        lucro = Math.round(lucro);
        lucro = lucro/100;

        volume = volume*100;
        volume = Math.round(volume);
        volume = volume/100;
    }

    public double getImposto() {
        return imposto;
    }

    public void setImposto(double imposto) {
        this.imposto = imposto;
    }
}