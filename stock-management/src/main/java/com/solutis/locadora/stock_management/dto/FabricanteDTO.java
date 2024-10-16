package com.solutis.locadora.stock_management.dto;

import com.solutis.locadora.stock_management.model.Fabricante;

import java.util.HashSet;
import java.util.Set;

public record FabricanteDTO(Long id, String nome) {

    public FabricanteDTO(Long id, String nome){
        this.id = id;
        this.nome = nome;
    }

    public FabricanteDTO(String nome){
        this(null, nome);
    }
}