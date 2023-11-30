package com.corretora.service;

import com.corretora.dao.AtivoRepository;
import com.corretora.dao.TransacaoRepository;
import com.corretora.dto.TransacaoResumo;
import com.corretora.excecao.AcaoInvalidaException;
import com.corretora.excecao.QuantidadeInvalidaException;
import com.corretora.model.*;
import com.corretora.model.ativo.Ativo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@Service
public class TransacaoService {
	@Autowired
    private AtivoService ativoService;
	
	@Autowired
    private AutorizacaoService autorizacaoService;
    
    @Autowired
    private  PosicaoService posicaoService;
    
    @Autowired
    private ValidacaoService validacaoService;

    @Autowired
    private AtivoRepository ativoRepository;
    
    @Autowired
    private TransacaoRepository transacaoRepository;

    private Ativo ativoTransacao;

    private Transacao transacao;
    
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



    public List<TransacaoResumo> findFormattedTransacoes(){
        List<Object[]> resultados = transacaoRepository.recuperarResumoTransacoes(autorizacaoService.LoadUsuarioLogado().getId());
        return createResumo(resultados);
    }

    public List<TransacaoResumo> createResumo(List<Object[]> resultados){
        List<TransacaoResumo> resumos = new ArrayList<>();
        for (Object[] resultado : resultados) {

            int intTipo = (Byte) resultado[4];
            Date data = (Date) resultado[5];
            String tipoTransacao = null;
            String ticker= null;
            int quantidade = 1;
            double preco = 0;
            double total= 0;

            if(intTipo == 0){
                tipoTransacao = "COMPRA";
                ticker = (String) resultado[0];
                quantidade = (int) resultado[1];
                preco = (double) resultado[2];
                total = (double) resultado[3];
            }
            else if(intTipo == 1){
                tipoTransacao = "VENDA";
                ticker = (String) resultado[0];
                quantidade = (int) resultado[1];
                preco = (double) resultado[2];
                total = (double) resultado[3];
            }
            else if(intTipo == 2){
                tipoTransacao = "DEPOSITO";
                total = (double) resultado[3];
                preco = total;
                ticker =  tipoTransacao;
            }
            else if(intTipo == 3){
                tipoTransacao = "RETIRADA";
                total = (double) resultado[3];
                preco = total;
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


    public void createTransacaoAtivo(Ativo ativo, String quantidade, TipoTransacao tipoTransacao) throws QuantidadeInvalidaException, AcaoInvalidaException{
        transacao = new Transacao();
        long userId = autorizacaoService.LoadUsuarioLogado().getId();

        ativoTransacao = ativoRepository.findByIdentificador(ativo.getIdentificador());

        if (ativoTransacao == null) {
            ativoTransacao = ativoService.saveAtivo(ativo);
        } else {
            ativoTransacao.setPreco(ativo.getPreco());
            ativoTransacao = ativoService.saveAtivo(ativoTransacao);
        }

		validacaoService.validate(quantidade);

        transacao.setTipoTransacao(tipoTransacao);
        transacao.setQuantidade(Integer.parseInt(quantidade));
        transacao.setTodayData();
        transacao.setIdUsuario(userId);
        transacao.setPreco(ativoTransacao.getPreco());

        if(tipoTransacao == TipoTransacao.VENDA){
           createTransacaoVenda(ativoTransacao);
        }
        else if(tipoTransacao == TipoTransacao.COMPRA){
           createTransacaoCompra(ativoTransacao);
        }
        
        this.saveTransacao(transacao);
    }

    public void createTransacao(double valor, TipoTransacao tipoTransacao) throws AcaoInvalidaException {
        transacao = new Transacao();
        long userId = autorizacaoService.LoadUsuarioLogado().getId();

        transacao.setTipoTransacao(tipoTransacao);
        transacao.setTodayData();
        transacao.setIdUsuario(userId);
        
        if(tipoTransacao == TipoTransacao.SAIDA){
            createTransacaoSaida(valor);
        }
        else if(tipoTransacao == TipoTransacao.ENTRADA) {
            createTransacaoEntrada(valor);
        }

        this.saveTransacao(transacao);
    }

    public void createTransacaoSaida(double valor) throws AcaoInvalidaException{
    	validacaoService.validate(this.getSaldo(), valor);
    	
        transacao.setTotalTransacao(-valor);
    }

    public void createTransacaoEntrada(double valor) throws AcaoInvalidaException{
    	validacaoService.validate(valor);
    	
        transacao.setTotalTransacao(valor);
    }

    public void createTransacaoVenda(Ativo ativoTransacao) throws QuantidadeInvalidaException {

        double total = -(transacao.getQuantidade()) * transacao.getPreco();
        
        transacao.setIdAtivo(ativoTransacao.getId());
        transacao.setTotalTransacao(total);
        
        this.checkPosicao(ativoTransacao);
    }

    public void createTransacaoCompra(Ativo ativoTransacao) throws AcaoInvalidaException, QuantidadeInvalidaException {
        double total = transacao.getQuantidade() * transacao.getPreco();

        validacaoService.validate(this.getSaldo(), total);
        
        transacao.setIdAtivo(ativoTransacao.getId());
        transacao.setTotalTransacao(total);

        this.checkPosicao(ativoTransacao);
    }


    public void checkPosicao(Ativo ativoTransacao) throws QuantidadeInvalidaException {
        Posicao posicao = posicaoService.findPosicaoByIdAtivo(this.ativoTransacao.getId());

        if(posicao == null){
            posicaoService.setPosicao(transacao,ativoTransacao);
        }
        else if (transacao.getTipoTransacao() == TipoTransacao.COMPRA ){
            posicaoService.atualizarPosicaoCompra(transacao,posicao);
        }
        else if (transacao.getTipoTransacao() == TipoTransacao.VENDA ){
            posicaoService.atualizarPosicaoVenda(transacao,posicao,ativoTransacao);
        }
    }

}
