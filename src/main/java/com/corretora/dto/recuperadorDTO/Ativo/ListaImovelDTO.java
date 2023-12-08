package com.corretora.dto.recuperadorDTO.Ativo;

import com.corretora.dto.recuperadorDTO.Ativo.AtivoDTO;
import com.corretora.dto.recuperadorDTO.Ativo.ImovelDTO.ImovelDTO;

import java.util.ArrayList;
import java.util.List;

public class ListaImovelDTO implements AtivoDTO {
    public List<ImovelDTO> imovelDTOList;

    public ListaImovelDTO(ArrayList<ImovelDTO> data) {
        imovelDTOList = data;
    }
}
