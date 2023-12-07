package com.corretora.controller;

import com.corretora.dto.recuperadorDTO.Ativo.ImovelDTO;
import com.corretora.excecao.AcaoInvalidaException;
import com.corretora.excecao.QuantidadeInvalidaException;
import com.corretora.model.ativo.Imovel;
import com.corretora.model.TipoTransacao;
import com.corretora.service.AtivoService;
import com.corretora.service.PosicaoService;
import com.corretora.service.TransacaoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Validated
public class VenderController {

    @Autowired
    private TransacaoService transacaoService;

    @Autowired
    private PosicaoService posicaoService;

    @Autowired
    private AtivoService ativoService;

    private ImovelDTO result;


    @GetMapping("acao/vender")
    public String pesquisaVenderAcao(Model model){
        List<String> tickers = posicaoService.findTickers();
        model.addAttribute("tickers", tickers);
        return "formVenderAcao";
    }


    @PostMapping("acao/vender")
    public String getVenderAcao(Model model, @RequestParam String ticker, RedirectAttributes redirectAttributes) throws JsonProcessingException {
        model.addAttribute("ticker",ticker);
        try{

            result = (ImovelDTO) ativoService.recuperarAtivo(ticker);

            model.addAttribute("symbol",result.ticker);
            model.addAttribute("price",result.price);

        }catch (AcaoInvalidaException aie){
            model.addAttribute("errorMessage",aie.getMessage());
            return "error/acaoError";
        }


        redirectAttributes.addAttribute("ticker", result.ticker);
        return "redirect:/acao/vender/{ticker}";
    }

    @PostMapping("acao/acaoVender")
    public String vender(Model model, @RequestParam String quantidade){
        model.addAttribute("quantidade", quantidade);
        try{

            this.transacaoService.createTransacaoAtivo(new Imovel(result.ticker, result.price),quantidade, TipoTransacao.VENDA);

        }catch (QuantidadeInvalidaException qie){
            model.addAttribute("errorMessage",qie.getMessage());
            return "error/quantidadeError";
        }catch (AcaoInvalidaException aie){
            model.addAttribute("errorMessage",aie.getMessage());
            return "error/acaoError";
        }


        return "redirect:/portifolio";
    }


    @GetMapping("/acao/vender/{ticker}")
    public String showComprarAcao(Model model) {
        model.addAttribute("symbol",result.ticker);
        model.addAttribute("price",result.price);
        return "venderAcao";
    }




}
