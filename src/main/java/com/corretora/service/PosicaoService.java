package com.corretora.service;

import com.corretora.dao.AtivoRepository;
import com.corretora.dao.PosicaoRepository;
import com.corretora.dto.PosicaoDTO;
import com.corretora.excecao.QuantidadeInvalidaException;
import com.corretora.model.*;
import com.corretora.model.ativo.Ativo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PosicaoService {
    @Autowired
    private PosicaoRepository posicaoRepository;

    @Autowired
    AutorizacaoService autorizacaoService;

    @Autowired
    private ResultadoService resultadoService;

    @Autowired
    private AtivoRepository ativoRepository;

    private Ativo ativo;


    public Posicao findPosicaoByIdentificador(String identificador){
        return this.posicaoRepository.findPosicaoByIdentificador(identificador,autorizacaoService.LoadUsuarioLogado().getId());
    }

    public Posicao findPosicaoByIdAtivo(Long idAtivo){
        return this.posicaoRepository.findPosicaoByIdAtivo(idAtivo,autorizacaoService.LoadUsuarioLogado().getId());
    }

    public void save(Posicao posicao){
        this.posicaoRepository.save(posicao);
    }

    public List<String> findTickers(){ return this.posicaoRepository.findTickers(autorizacaoService.LoadUsuarioLogado().getId());}

    public void setPosicao(Transacao transacao){
        Posicao posicao = new Posicao();
        Optional<Ativo> ativo1 = ativoRepository.findById(transacao.getIdAtivo());
        ativo1.ifPresent(value -> ativo = ativo1.get());

        posicao.setIdAtivo(transacao.getIdAtivo());
        posicao.setQuantidadeTotal(transacao.getQuantidade());
        posicao.setPrecoMedio(ativo.getPreco());
        posicao.setValorTotal(transacao.getTotal());
        posicao.setIdUsuario(autorizacaoService.LoadUsuarioLogado().getId());
        posicao.setStatusPosicao();


        this.save(posicao);
    }


    public List<PosicaoDTO> findFormattedPosicoes(){
        List<Object[]> objPosicoes = this.posicaoRepository.findFormattedPosicoes(autorizacaoService.LoadUsuarioLogado().getId());

        return formatPosicao(objPosicoes);
    }

    public List<PosicaoDTO> formatPosicao(List<Object[]> objPosicoes){
        List<PosicaoDTO> posicoesList = new ArrayList<> ();
        for (Object[] objPosicao : objPosicoes) {
            String ticker = (String) objPosicao[0];
            double precoMedio = (double) objPosicao[1];
            int quantidadeTotal = (int) objPosicao[2];
            double valorTotal = (double) objPosicao[3];

           PosicaoDTO posicao = new PosicaoDTO(ticker, quantidadeTotal, precoMedio,valorTotal);

            posicoesList.add(posicao);
        }
        return posicoesList;
    }
    @Transactional
    public void atualizarPosicaoCompra(Transacao transacao, Posicao posicao) {
            int novaQuantidade = transacao.getQuantidade() + posicao.getQuantidadeTotal();
            posicao.setQuantidadeTotal(novaQuantidade);

            double total = posicao.getValorTotal() + transacao.getTotal();
            posicao.setValorTotal(total);

            double novoPrecoMedio = (posicao.getValorTotal() / posicao.getQuantidadeTotal());
            posicao.setPrecoMedio(novoPrecoMedio);

            posicao.setStatusPosicao();

            posicaoRepository.save(posicao);

    }

    @Transactional
    public void atualizarPosicaoVenda(Transacao transacao, Posicao posicao) throws QuantidadeInvalidaException {
        if(posicao.getQuantidadeTotal() - transacao.getQuantidade() < 0){
            throw new QuantidadeInvalidaException("QUANTIDADE MAXIMA DE VENDA: "  + posicao.getQuantidadeTotal());
        }
        int novaQuantidade = -(transacao.getQuantidade()) + posicao.getQuantidadeTotal();
        posicao.setQuantidadeTotal(novaQuantidade);

        double resultadoFinanceiro = (ativo.getPreco() - posicao.getPrecoMedio()) * transacao.getQuantidade();
        Resultado resultado = new Resultado(ativo.getIdentificador(), transacao.getTotal(), resultadoFinanceiro, (resultadoFinanceiro / (transacao.getQuantidade() * posicao.getPrecoMedio()) * 100),posicao.getIdUsuario() );
        resultadoService.saveResultado(resultado);
        LocalDate localDate = resultado.getData().toLocalDate();

        resultadoService.calcularIR(localDate.getMonthValue(),localDate.getYear());

        double total = posicao.getValorTotal() + transacao.getTotal()  +  resultadoFinanceiro;
        posicao.setValorTotal(total);


        posicao.setStatusPosicao();


        if(posicao.getQuantidadeTotal() == 0){               //identificador
            posicaoRepository.deleteByTicker(ativo.getIdentificador(),autorizacaoService.LoadUsuarioLogado().getId());
        }
        else{
            posicaoRepository.save(posicao);
        }


    }



}
