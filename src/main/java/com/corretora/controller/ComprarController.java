package com.corretora.controller;

import com.corretora.dto.recuperadorDTO.Ativo.AtivoDTO;
import com.corretora.dto.recuperadorDTO.Ativo.ImovelDTO.ImovelDTO;
import com.corretora.dto.recuperadorDTO.Ativo.ListaImovelDTO;
import com.corretora.excecao.AcaoInvalidaException;
import com.corretora.excecao.QuantidadeInvalidaException;
import com.corretora.model.ativo.Imovel;
import com.corretora.model.TipoTransacao;
import com.corretora.service.AtivoService;
import com.corretora.service.TransacaoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ComprarController {

    @Autowired
    private TransacaoService transacaoService;

    @Autowired
    private AtivoService ativoService;

    private ImovelDTO result;


    @GetMapping("acao/comprar")
    public String pesquisaComprarAcao(Model model) {
        model.addAttribute("acao");
        return "formComprarAcao";
    }

    @PostMapping("acao/comprar")
    public String getComprarAcao(Model model,@RequestParam String ticker, RedirectAttributes redirectAttributes) throws JsonProcessingException {
        model.addAttribute("ticker",ticker);
        try{


            ListaImovelDTO resultLista = (ListaImovelDTO) ativoService.recuperarAtivo(ticker);

            result = resultLista.imovelDTOList.get(0);
            model.addAttribute("symbol",result.propertyId );
            model.addAttribute("price",result.price );
            model.addAttribute("url",result.url);


        }catch (AcaoInvalidaException aie){
            model.addAttribute("errorMessage",aie.getMessage());
            return "error/acaoError";
        }

        redirectAttributes.addAttribute("ticker", result.propertyId);
        redirectAttributes.addAttribute("url", result.url);
        return "redirect:/acao/comprar/{ticker}";

    }

    @PostMapping("/acaoComprar")
    public String comprar(Model model, @RequestParam String quantidade) {
        model.addAttribute("quantidade", quantidade);
        try{
            double preco = Double.parseDouble(result.price);
            this.transacaoService.createTransacaoAtivo(new Imovel(result.propertyId, preco),quantidade, TipoTransacao.COMPRA);

        }catch (QuantidadeInvalidaException qie){
            model.addAttribute("errorMessage",qie.getMessage());
            return "error/quantidadeError";
        }catch (AcaoInvalidaException aie){
            model.addAttribute("errorMessage",aie.getMessage());

            return "error/acaoError";
        }

        return "redirect:/portifolio";
    }

    @GetMapping("/acao/comprar/{ticker}")
    public String showComprarAcao(Model model) {
        model.addAttribute("symbol",result.propertyId);
        model.addAttribute("price",result.price);
        model.addAttribute("url",result.url);
        return "comprarAcao";
    }


}
