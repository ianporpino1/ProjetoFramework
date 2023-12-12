package com.corretora.service.strategyValidacao;

import com.corretora.excecao.AcaoInvalidaException;
import com.corretora.excecao.QuantidadeInvalidaException;
import org.springframework.stereotype.Service;

@Service
public class ValidacaoImovel implements ValidacaoAtivo{


    @Override
    public void validateQuantidade(String quantidade) throws QuantidadeInvalidaException {
        if(quantidade.isBlank()){
            throw new QuantidadeInvalidaException("Quantidade Obrigatoria");
        }
        int intQuantidade = Integer.parseInt(quantidade);
        if(intQuantidade <= 0){
            throw new QuantidadeInvalidaException("Quantidade Deve Ser Maior que 0");
        }
        if(intQuantidade > 1){
            throw new QuantidadeInvalidaException("Quantidade Deve Ser Igual a 1");
        }
    }

    @Override
    public void validateSaldo(double saldo, double valor) throws AcaoInvalidaException {
        if (saldo - valor < 0) {
            throw new AcaoInvalidaException("Saldo insuficiente para realizar a operação");
        }
    }

    @Override
    public void validateDepositoRetirada(double valor) throws AcaoInvalidaException {
        if(Double.isNaN(valor)){
            throw new AcaoInvalidaException("Saldo insuficiente para realizar a operação");
        }
        if (valor <= 0) {
            throw new AcaoInvalidaException("O deposito ou retirada precisa ser maior que 0");
        }
    }
}
