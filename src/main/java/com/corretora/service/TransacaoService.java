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
    private TransacaoRepository transacaoRepository;

    @Autowired
    private AutorizacaoService autorizacaoService;

    @Autowired
    private  PosicaoService posicaoService;

    @Autowired
    private AtivoService ativoService;

    @Autowired
    private AtivoRepository ativoRepository;

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
    //REFATORAR PARA TEMPLATE METHOD //ATIVO = ACAO, IMOVEL, VEICULO
    public void createTransacao(Ativo ativo, String quantidade, TipoTransacao tipoTransacao) throws QuantidadeInvalidaException, AcaoInvalidaException{
        transacao = new Transacao();
        long userId = autorizacaoService.LoadUsuarioLogado().getId();

        ativoTransacao = ativoRepository.findByIdentificador(ativo.getIdentificador());



        if (ativoTransacao == null) {
            ativoTransacao = ativoService.saveAtivo(ativo);
        } else {
            ativoTransacao.setPreco(ativo.getPreco());
            ativoTransacao = ativoService.saveAtivo(ativoTransacao);
        }


        Long ativoId = ativoTransacao.getId();
        
        if(quantidade == ""){
            throw new QuantidadeInvalidaException("Quantidade Obrigatoria");
        }
        int intQuantidade = Integer.parseInt(quantidade);
        if(intQuantidade <= 0){
            throw new QuantidadeInvalidaException("Quantidade Deve Ser Maior que 0");
        }

        //chama ValidacaoService.validate();

        transacao.setTipoTransacao(tipoTransacao);
        transacao.setQuantidade(intQuantidade);
        transacao.setTodayData();
        transacao.setIdUsuario(userId);

        transacao.setPreco(ativoTransacao.getPreco());

        
        if(tipoTransacao == TipoTransacao.SAIDA){
            createTransacaoSaida();
        } else if(tipoTransacao == TipoTransacao.ENTRADA){
            createTransacaoEntrada();
        } else if(tipoTransacao == TipoTransacao.VENDA){
            createTransacaoVenda();
        } else if(tipoTransacao == TipoTransacao.COMPRA){
            createTransacaoCompra();
       }
        
        this.saveTransacao(transacao);
    }

    public void createTransacaoSaida() throws AcaoInvalidaException{
        transacao.setPreco(transacao.getPreco());
        double total = -(transacao.getQuantidade()) * transacao.getPreco();
        if (this.getSaldo() - total < 0) {
            throw new AcaoInvalidaException("Saldo insuficiente para realizar a retirada");
        } else {
            transacao.setTotalTransacao(total);
        }
        //chama ValidacaoService.validate();
    }

    public void createTransacaoEntrada() throws AcaoInvalidaException{
        if (transacao.getPreco() <= 0) {
            throw new AcaoInvalidaException("O deposito precisa ser maior que 0");
        } else {
            transacao.setTotalTransacao(transacao.getPreco());
            transacao.setIdUsuario(autorizacaoService.LoadUsuarioLogado().getId());
            saveTransacao(transacao);
        }
    }

    public void createTransacaoVenda() throws QuantidadeInvalidaException {

        double total = -(transacao.getQuantidade()) * transacao.getPreco();
        transacao.setIdAtivo(ativoTransacao.getId());
        transacao.setTotalTransacao(total);
        this.checkPosicao();
    }

    public void createTransacaoCompra() throws AcaoInvalidaException, QuantidadeInvalidaException {
        double total = transacao.getQuantidade() * transacao.getPreco();

        if (this.getSaldo() - total < 0) {
            throw new AcaoInvalidaException("Saldo insuficiente para realizar a compra");
        } else {
            transacao.setIdAtivo(ativoTransacao.getId());
            transacao.setTotalTransacao(total);
        }

        this.checkPosicao();
    }


    public void checkPosicao() throws QuantidadeInvalidaException {
        Posicao posicao = posicaoService.findPosicaoByIdAtivo(ativoTransacao.getId()); //IDENTIFICADOR(ACAO: TICKER, IMOVEL: SEQUENCIAL, CARRO: PLACA)

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
