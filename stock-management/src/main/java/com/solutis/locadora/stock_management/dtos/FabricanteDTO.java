package com.solutis.locadora.stock_management.dtos;

import java.util.Set;

public record FabricanteDTO(
        Long id,
        String nome,
        Set<ModeloCarroDTO> modeloCarroDTOS)
{
}
