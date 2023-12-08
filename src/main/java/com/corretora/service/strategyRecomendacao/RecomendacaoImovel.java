package com.corretora.service.strategyRecomendacao;

import com.corretora.dto.recuperadorDTO.Informacoes.InformacoesDTO;
import com.corretora.dto.recuperadorDTO.Informacoes.ListaInfoImovel;
import com.corretora.dto.recuperadorDTO.Informacoes.RecomendacaoImovelDTO.InfoImovelDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class RecomendacaoImovel implements GeradorRecomendacao{

    @Override
    public List<Double> gerarRecomendacao(InformacoesDTO informacoesDTO) {
        double nota=0;
        if (informacoesDTO instanceof ListaInfoImovel listaInfoImovel) {
            List<InfoImovelDTO> list = listaInfoImovel.infoImovelDTOList;

            for(InfoImovelDTO lugar: list){
                if(lugar.id >= 17069 && lugar.id <= 17070){
                    nota+=1;
                }
                if(lugar.id >= 12044 && lugar.id <= 12061){
                    nota+=1.5;
                }
                if(lugar.id >= 13026 && lugar.id <= 13065){
                    nota+=0.5;
                }
            }
        }


        ArrayList<Double> notaFinal = new ArrayList<>();
        notaFinal.add(nota);
        return notaFinal;
    }
}
