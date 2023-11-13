package com.corretora.service;

import com.corretora.dao.TransacaoRepository;
import com.corretora.dto.TransacaoResumo;
import com.corretora.excecao.AcaoInvalidaException;
import com.corretora.excecao.QuantidadeInvalidaException;
import com.corretora.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@Service
public class TransacaoService {
    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private AutorizacaoService autorizacaoService;

    @Autowired
    private  PosicaoService posicaoService;
    
    public double getSaldo(){
    	List<Object[]> saldoBruto = transacaoRepository.calcularSaldo(autorizacaoService.LoadUsuarioLogado().getId());
    	
    	if (saldoBruto.size() == 0) {
    		return 0;
    	} else {
    		double total = 0;
    		for (Object[] saldo : saldoBruto) {
                int intTipo = (Byte) saldo[1];
                if(intTipo == 0){
                    saldo[0] = -(Double)saldo[0];
                }
                else if(intTipo == 1){
                    saldo[0] = - (Double)saldo[0];
                }

    			total += (Double)saldo[0];

    		}
    		return total;
    	}
    }

    public List<Transacao> findAllTransacao(){

        return (List<Transacao>) transacaoRepository.findAll();
    }
    

    public List<TransacaoResumo> findFormattedTransacoes(){
        List<Object[]> resultados = transacaoRepository.calcularResumoTransacoes(autorizacaoService.LoadUsuarioLogado().getId());

        return createResumo(resultados);
    }

    public List<TransacaoResumo> createResumo(List<Object[]> resultados){
        List<TransacaoResumo> resumos = new ArrayList<>();
        for (Object[] resultado : resultados) {
            String ticker = (String) resultado[0];
            int quantidade = (int) resultado[1];
            double preco = (double) resultado[2];
            double total = (double) resultado[3];
            int intTipo = (Byte) resultado[4];
            Date data = (Date) resultado[5];

            String tipoTransacao = null;
            if(intTipo == 0){
                tipoTransacao = "COMPRA";
            }
            else if(intTipo == 1){
                tipoTransacao = "VENDA";
            }
            else if(intTipo == 2){
                tipoTransacao = "DEPOSITO";
                ticker =  tipoTransacao;
            }
            else if(intTipo == 3){
                tipoTransacao = "RETIRADA";
                ticker =  tipoTransacao;
            }

            String dataFormatted = null;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            dataFormatted = simpleDateFormat.format(data);

            TransacaoResumo resumo = new TransacaoResumo(ticker, quantidade, preco,total,tipoTransacao,dataFormatted);
            resumos.add(resumo);
        }
        return resumos;
    }

    public void saveTransacao(Transacao transacao){

        transacaoRepository.save(transacao);
    }

    public void setTransacao(Acao acao, String quantidade, TipoTransacao tipoTransacao) throws QuantidadeInvalidaException, AcaoInvalidaException{
        Transacao transacao = new Transacao();
        long userId = autorizacaoService.LoadUsuarioLogado().getId();
        
        if(quantidade == ""){
            throw new QuantidadeInvalidaException("Quantidade Obrigatoria");
        }
        int intQuantidade = Integer.parseInt(quantidade);
        if(intQuantidade <= 0){
            throw new QuantidadeInvalidaException("Quantidade Deve Ser Maior que 0");
        }
        
        transacao.setAcao(acao);
        transacao.setTipoTransacao(tipoTransacao);
        transacao.setQuantidade(intQuantidade);
        transacao.setTodayData();
        transacao.setIdUsuario(userId);
        
        if(tipoTransacao == TipoTransacao.SAIDA){
            setTransacaoSaida(transacao);
        } else if(tipoTransacao == TipoTransacao.ENTRADA){
            setTransacaoEntrada(transacao);
        } else if(tipoTransacao == TipoTransacao.VENDA){
            setTransacaoVenda(transacao);
        } else if(tipoTransacao == TipoTransacao.COMPRA){
            setTransacaoCompra(transacao);
       }
        
        this.saveTransacao(transacao);
    }

    public void setTransacaoSaida(Transacao transacao) throws AcaoInvalidaException{
        double total = -(transacao.getQuantidade()) * transacao.getAcao().getPreco();
        if (this.getSaldo() - total < 0) {
            throw new AcaoInvalidaException("Saldo insuficiente para realizar a retirada");
        } else {
            transacao.setTotalTransacao(total);
        }
    }

    public void setTransacaoEntrada(Transacao transacao) throws AcaoInvalidaException{
        if (transacao.getAcao().getPreco() <= 0) {
            throw new AcaoInvalidaException("O deposito precisa ser maior que 0");
        } else {
            transacao.setTotalTransacao(transacao.getAcao().getPreco());
        }
    }

    public void setTransacaoVenda(Transacao transacao) throws QuantidadeInvalidaException {
        double total = -(transacao.getQuantidade()) * transacao.getAcao().getPreco();
        transacao.setTotalTransacao(total);
        this.checkPosicao(transacao);
    }

    public void setTransacaoCompra(Transacao transacao) throws AcaoInvalidaException, QuantidadeInvalidaException {
        double total = transacao.getQuantidade() * transacao.getAcao().getPreco();

        if (this.getSaldo() - total < 0) {
            throw new AcaoInvalidaException("Saldo insuficiente para realizar a compra");
        } else {
            transacao.setTotalTransacao(total);
        }

        this.checkPosicao(transacao);
    }


    public void checkPosicao(Transacao transacao) throws QuantidadeInvalidaException {
        Posicao posicao = posicaoService.findPosicaoByTicker(transacao.getAcao().getTicker());

        if(posicao == null){
            posicaoService.setPosicao(transacao);
        }
        else if (transacao.getTipoTransacao() == TipoTransacao.COMPRA ){
            posicaoService.atualizarPosicaoCompra(transacao,posicao);
        }
        else if (transacao.getTipoTransacao() == TipoTransacao.VENDA ){
            posicaoService.atualizarPosicaoVenda(transacao,posicao);
        }
    }

}
