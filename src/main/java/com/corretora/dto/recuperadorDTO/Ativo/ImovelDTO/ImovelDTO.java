package com.corretora.dto.recuperadorDTO.Ativo.ImovelDTO;

import com.corretora.dto.recuperadorDTO.Ativo.AtivoDTO;

import java.util.Date;

public class ImovelDTO implements AtivoDTO  {
    public String propertyId;
    public String listingId;
    public Date listDate;
    public String price;
    public Object priceMin;
    public Object priceMax;
    public String url;
    public String permalink;
    public String soldPrice;
    public String soldDate;
    public Location location;
}
