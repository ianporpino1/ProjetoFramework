package com.corretora.dto.recuperadorDTO.Ativo;

import com.corretora.dto.recuperadorDTO.Ativo.ImovelDTO.ImovelDTO;

import java.util.ArrayList;
import java.util.List;

public class ListaImovel implements AtivoDTO {
    public List<ImovelDTO> imovelDTOList;

    public ListaImovel(ArrayList<ImovelDTO> data) {
        imovelDTOList = data;
    }
}
