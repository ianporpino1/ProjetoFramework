package com.corretora.service.strategyRecomendacao;

import java.util.List;

public interface GeradorRecomendacao {

    public List<Double> gerarRecomendacao(Attributes attributes);
}
