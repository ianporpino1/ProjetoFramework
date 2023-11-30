package com.corretora.service;

import org.springframework.stereotype.Service;

import com.corretora.excecao.AcaoInvalidaException;
import com.corretora.excecao.QuantidadeInvalidaException;
import com.corretora.model.TipoTransacao;

@Service
public class ValidacaoService {
	
	public void validate(String quantidade) throws QuantidadeInvalidaException{
		if(quantidade.isBlank()){
            throw new QuantidadeInvalidaException("Quantidade Obrigatoria");
        }
        int intQuantidade = Integer.parseInt(quantidade);
        if(intQuantidade <= 0){
            throw new QuantidadeInvalidaException("Quantidade Deve Ser Maior que 0");
        }
	}
	
	public void validate(double saldo, double valor) throws AcaoInvalidaException{
		double total = -valor;
        if (saldo - total < 0) {
            throw new AcaoInvalidaException("Saldo insuficiente para realizar a operação");
        } 
	}
	
	public void validate(double valor) throws AcaoInvalidaException{
		if (valor <= 0) {
            throw new AcaoInvalidaException("O deposito precisa ser maior que 0");
        }
	}
}
