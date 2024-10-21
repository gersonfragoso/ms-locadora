package com.solutis.locadora.vehicle_rental_service.dto;


import com.solutis.locadora.vehicle_rental_service.enums.Categoria;

public record ModeloCarroDTO(
        Long id,
        String descricao,
        Categoria categoria,
        Long fabricanteId) // Apenas o ID do fabricante
{
    public ModeloCarroDTO(Long id, String descricao, Categoria categoria, Long fabricanteId) {
        this.id = id;
        this.descricao = descricao;
        this.categoria = categoria;
        this.fabricanteId = fabricanteId;
    }

    public ModeloCarroDTO(String descricao, Categoria categoria, Long fabricanteId) {
        this(null, descricao, categoria, fabricanteId);
    }
}