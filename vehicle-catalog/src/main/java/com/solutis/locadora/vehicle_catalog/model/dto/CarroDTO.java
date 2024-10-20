package com.solutis.locadora.vehicle_catalog.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public record CarroDTO
    (Long id,
    String placa,
    String cor,
    String categoria,
    BigDecimal valorDiaria,
    Set<Long> acessorioIds,
    Long modeloCarroId,
    Set<LocalDate> datasOcupadas)

{
    public CarroDTO(Long id, String placa, String cor, String categoria, BigDecimal valorDiaria,
            Set<Long> acessorioIds, Long modeloCarroId, Set<LocalDate> datasOcupadas) {
        this.id = id;
        this.placa = placa;
        this.cor = cor;
        this.categoria = categoria;
        this.valorDiaria = valorDiaria;
        this.acessorioIds = acessorioIds;
        this.modeloCarroId = modeloCarroId;
        this.datasOcupadas = datasOcupadas;
    }

}
