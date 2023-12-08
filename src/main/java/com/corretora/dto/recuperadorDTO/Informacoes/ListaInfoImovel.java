package com.corretora.dto.recuperadorDTO.Informacoes;

import com.corretora.dto.recuperadorDTO.Informacoes.InformacoesDTO;
import com.corretora.dto.recuperadorDTO.Informacoes.RecomendacaoImovelDTO.InfoImovelDTO;

import java.util.ArrayList;
import java.util.List;

public class ListaInfoImovel implements InformacoesDTO {
    public List<InfoImovelDTO> infoImovelDTOList;

    public ListaInfoImovel(ArrayList<InformacoesDTO> categories) {
    }
}
