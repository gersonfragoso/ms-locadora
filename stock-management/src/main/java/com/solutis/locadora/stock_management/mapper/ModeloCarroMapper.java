package com.solutis.locadora.stock_management.mapper;

import com.solutis.locadora.stock_management.dto.FabricanteDTO;
import com.solutis.locadora.stock_management.dto.ModeloCarroDTO;
import com.solutis.locadora.stock_management.model.Fabricante;
import com.solutis.locadora.stock_management.model.ModeloCarro;
import com.solutis.locadora.stock_management.repository.FabricanteRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ModeloCarroMapper {


    // Converte ModeloCarro Entity para DTO
    public static ModeloCarroDTO modeloCarroToDTO(ModeloCarro modeloCarro) {
        return new ModeloCarroDTO(
                modeloCarro.getId(),
                modeloCarro.getDescricao(),
                modeloCarro.getCategoria(),
                modeloCarro.getFabricante().getId()); // Apenas o ID do fabricante
    }

    // Converte ModeloCarroDTO para Entity
    public static ModeloCarro modeloCarroToEntity(ModeloCarroDTO modeloCarroDTO, Fabricante fabricante) {
        return new ModeloCarro(modeloCarroDTO.descricao(), modeloCarroDTO.categoria(), fabricante);
    }
}
