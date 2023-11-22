package com.corretora.dto.apiResult;


import com.corretora.dto.recuperadorDTO.InformacoesDTO;

public class AcaoDTO extends InformacoesDTO {
    public double price;
    public double change_point;
    public double change_percentage;
    public String total_vol;
    public String ticker;
}
