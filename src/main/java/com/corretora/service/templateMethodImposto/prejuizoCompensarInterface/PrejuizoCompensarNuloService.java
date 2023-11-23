package com.corretora.service.templateMethodImposto.prejuizoCompensarInterface;

import org.springframework.stereotype.Service;

@Service
public class PrejuizoCompensarNuloService implements PrejuizoCompensarService{
    @Override
    public void setPrejuizoCompensar(double total) {
        // Implementação vazia, não há lógica de prejuízo a compensar para este tipo de ativo
    }
}
