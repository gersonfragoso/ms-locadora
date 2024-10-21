package com.solutis.locadora.vehicle_rental_service.dto;

public record AcessorioDTO(Long id, String descricao)

{

    public AcessorioDTO (String descricao){
        this(null,descricao);
    }

    public AcessorioDTO (Long id, String descricao){
        this.id = id;
        this.descricao = descricao;
    }

}
