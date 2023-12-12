package com.corretora.service.strategyValidacao;

import com.corretora.excecao.AcaoInvalidaException;
import com.corretora.excecao.QuantidadeInvalidaException;

public interface ValidacaoAtivo {
    public void validateQuantidade(String quantidade) throws QuantidadeInvalidaException;
    public void validateSaldo(double saldo, double valor) throws AcaoInvalidaException;
    public void validateDepositoRetirada(double valor) throws AcaoInvalidaException;
}
