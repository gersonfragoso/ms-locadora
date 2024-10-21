package com.solutis.locadora.vehicle_rental_service.dto;

public record FabricanteDTO(Long id, String nome)

{
    public FabricanteDTO(Long id, String nome){
        this.id = id;
        this.nome = nome;
    }

    public FabricanteDTO(String nome){
        this(null, nome);
    }
}
