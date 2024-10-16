package com.solutis.locadora.stock_management.dto;


public record AcessorioDTO (
        Long id,
        String descricao){

    public AcessorioDTO (String descricao){
        this(null,descricao);
    }
}
