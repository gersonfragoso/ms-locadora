package com.solutis.locadora.stock_management.mapper;

import com.solutis.locadora.stock_management.dto.FabricanteDTO;
import com.solutis.locadora.stock_management.dto.ModeloCarroDTO;
import com.solutis.locadora.stock_management.model.Fabricante;
import com.solutis.locadora.stock_management.model.ModeloCarro;

public class ModeloCarroMapper {

    public static ModeloCarroDTO modeloCarroToDTO(ModeloCarro modeloCarro) {
        return new ModeloCarroDTO(
                modeloCarro.getId(),
                modeloCarro.getDescricao(),
                modeloCarro.getCategoria(),
                modeloCarro.getFabricante().getId());
    }

    public static ModeloCarro modeloCarroToEntity(ModeloCarroDTO modeloCarroDTO, Fabricante fabricante) {
        return new ModeloCarro(modeloCarroDTO.descricao(), modeloCarroDTO.categoria(), fabricante);
    }

}
