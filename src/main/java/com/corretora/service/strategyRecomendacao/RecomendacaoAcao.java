package com.corretora.service.strategyRecomendacao;

import com.corretora.dto.recuperadorDTO.Informacoes.RecomendacaoAcaoDTO.Attributes;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class RecomendacaoAcao implements GeradorRecomendacao{

    @Override
    public List<Double> gerarRecomendacao(Attributes attributes) {
        List<Double> resultado = new ArrayList<>();
        double retornoEmPorcentagemEsperado = (1/attributes.evEbitda)*100;
        retornoEmPorcentagemEsperado = retornoEmPorcentagemEsperado*100;
        retornoEmPorcentagemEsperado = Math.round(retornoEmPorcentagemEsperado);
        retornoEmPorcentagemEsperado = retornoEmPorcentagemEsperado/100;

        double priceEarningsRatio = attributes.lastClosePriceEarningsRatio*100;
        priceEarningsRatio = Math.round(priceEarningsRatio);
        priceEarningsRatio = priceEarningsRatio/100;

        resultado.add(retornoEmPorcentagemEsperado);
        resultado.add(priceEarningsRatio);

        return resultado;
    }
}
