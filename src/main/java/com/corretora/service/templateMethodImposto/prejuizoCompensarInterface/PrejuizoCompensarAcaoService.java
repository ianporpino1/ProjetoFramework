package com.corretora.service.templateMethodImposto.prejuizoCompensarInterface;

import com.corretora.model.Resultado;
import com.corretora.service.AutorizacaoService;
import com.corretora.service.ResultadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
@Service
public class PrejuizoCompensarAcaoService implements PrejuizoCompensarService{

    @Autowired
    private ResultadoService resultadoService;

    @Autowired
    private AutorizacaoService autorizacaoService;

    @Override
    public void setPrejuizoCompensar(double total){
        Resultado prejuizo = resultadoService.findResultadoByName("prejuizoCompensar");
        if(prejuizo==null){
            Resultado prejuizoCompensar = new Resultado();
            prejuizoCompensar.setAtivo("prejuizoCompensar");
            prejuizoCompensar.setResultado(total);
            prejuizoCompensar.setIdUsuario(autorizacaoService.LoadUsuarioLogado().getId());
            prejuizoCompensar.setData(Date.valueOf(LocalDate.now().plusMonths(1)));
            resultadoService.saveResultado(prejuizoCompensar);

        }
        else{
            atualizarPrejuizoCompensar(prejuizo,total);
        }
    }

    //opcional(somente para acoes)
    public void atualizarPrejuizoCompensar(Resultado prejuizo, double total){
        if(total > 0)
            prejuizo.setResultado(0);
        else
            prejuizo.setResultado(total);
        resultadoService.saveResultado(prejuizo);
    }
}
