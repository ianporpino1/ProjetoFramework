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
        if (informacoesDTO instanceof ListaInfoImovel) {
            List<InfoImovelDTO> list =  ((ListaInfoImovel) informacoesDTO).infoImovelDTOList;

            double escola=0;
            double restaurante=0;
            double store=0;
            for(InfoImovelDTO lugar: list){
                System.out.println(lugar.name);
                if(nota>=10){
                    nota=10;
                    break;
                }
                if(escola <= 3 && lugar.distance <=500 && lugar.name.contains("School")){
                    nota+=1.5;
                    escola++;
                }
                if(restaurante <= 5 && lugar.distance <=500 && lugar.name.contains("Restaurant")){
                    nota+=0.5;
                    restaurante++;
                }
                if( store <= 2 && lugar.distance <=500 && (lugar.name.contains("Grocery") || lugar.name.contains("Store"))){
                    nota+=1.5;
                    store++;
                }

            }
        }


        ArrayList<Double> notaFinal = new ArrayList<>();
        notaFinal.add(nota);
        return notaFinal;
    }
}
