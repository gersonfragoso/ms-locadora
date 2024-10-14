package com.solutis.locadora.stock_management.dto;


import com.solutis.locadora.stock_management.model.utils_enum.Categoria;

public record ModeloCarroDTO (Long id,
                              String descricao,
                              Categoria categoria,
                              Long fabricanteId) {
}