package com.corretora.controller;

import com.corretora.dto.ResultadoDTO;
import com.corretora.model.Imposto;
import com.corretora.service.ResultadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ImpostoRendaController {

    @Autowired
    private ResultadoService resultadoService;

    @GetMapping("/calculadora-imposto-de-renda")
    public String showCalculadora(Model model){


        return "impostoRenda";
    }


    @PostMapping("/calculadora-imposto-de-renda")
    public String calcularIR(Model model,@RequestParam int mes,@RequestParam int ano){

        List<ResultadoDTO> resultadosList = resultadoService.findAllResultadoByData(mes,ano);

        Imposto imposto =  resultadoService.calcularIR(mes,ano);

        model.addAttribute("resultadosList", resultadosList);
        model.addAttribute("valorImposto", imposto.getValorImposto());
        model.addAttribute("resultadoTotal",imposto.getLucro());

        return "impostoCalculado";
    }

}
