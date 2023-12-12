package com.corretora.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.corretora.excecao.AcaoInvalidaException;
import com.corretora.model.TipoTransacao;
import com.corretora.service.TransacaoService;

@Controller
@Validated
public class RetirarController {
	
    @Autowired
    private TransacaoService transacaoService;
    
    @GetMapping("/retirar")
    public String formRemocao(Model model) {
    	model.addAttribute("caixa", transacaoService.getSaldo());
    	return "Retirar";
    }
    
    @PostMapping("/retirar") 
    public String retirar(Model model, @RequestParam(defaultValue = "0.0") double valor) {
    	try {

            this.transacaoService.createTransacao(valor, TipoTransacao.SAIDA);
    		
    	} catch (AcaoInvalidaException aie){
            model.addAttribute("errorMessage",aie.getMessage());
            return "error/acaoError";
        }
    	
    	return "redirect:/portifolio";
    }
}
