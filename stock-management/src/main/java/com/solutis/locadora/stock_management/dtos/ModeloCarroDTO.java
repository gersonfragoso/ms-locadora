package com.solutis.locadora.stock_management.dtos;

import com.solutis.locadora.stock_management.model.Categoria;

public record ModeloCarroDTO (Long id,
                              String descricao,
                              Categoria categoria,
                              Long fabricanteId) {
}
