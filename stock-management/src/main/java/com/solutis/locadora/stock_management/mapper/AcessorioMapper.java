package com.solutis.locadora.stock_management.mapper;

import com.solutis.locadora.stock_management.dto.AcessorioDTO;
import com.solutis.locadora.stock_management.model.Acessorio;
import com.solutis.locadora.stock_management.repository.AcessorioRepository;

public class AcessorioMapper {
    public static AcessorioDTO acessorioToDTO(Acessorio acessorio) {
        return new AcessorioDTO(acessorio.getId(), acessorio.getDescricao());
    }

    public static Acessorio acessorioToEntity(AcessorioDTO acessorioDTO) {
        Acessorio acessorio = new Acessorio();
        acessorio.setId(acessorioDTO.id());
        acessorio.setDescricao(acessorioDTO.descricao());
        return acessorio;
    }
}
