package com.solutis.locadora.stock_management.dto;

import com.solutis.locadora.stock_management.model.utils_enum.Categoria;


public record ModeloCarroDTO(
        Long id,
        String descricao,
        Categoria categoria,
        Long fabricanteId)
{
    public ModeloCarroDTO(Long id, String descricao, Categoria categoria, Long fabricanteId) {
        this.id = id;
        this.descricao = descricao;
        this.categoria = categoria;
        this.fabricanteId = fabricanteId;
    }

    public ModeloCarroDTO(String descricao, Categoria categoria, Long fabricanteId){
        this(null, descricao, categoria, fabricanteId);
    }
}