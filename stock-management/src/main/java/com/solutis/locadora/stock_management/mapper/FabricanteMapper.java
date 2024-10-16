package com.solutis.locadora.stock_management.mapper;

import com.solutis.locadora.stock_management.dto.FabricanteDTO;
import com.solutis.locadora.stock_management.model.Fabricante;

import java.util.stream.Collectors;

public class FabricanteMapper {
    public static FabricanteDTO fabricanteToDTO(Fabricante fabricante) {
        return new FabricanteDTO(fabricante.getId(), fabricante.getNome());
    }
    public static Fabricante fabricanteToEntity(FabricanteDTO fabricanteDTO) {
        return new Fabricante(fabricanteDTO.id(), fabricanteDTO.nome(), null);
    }
}
