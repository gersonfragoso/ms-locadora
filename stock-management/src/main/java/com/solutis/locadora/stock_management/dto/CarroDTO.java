package com.solutis.locadora.stock_management.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.Set;

public record CarroDTO(
        Long id,
        String placa,
        String chassi,
        String cor,
        String categoria,
        BigDecimal valorDiaria,
        Set<Long> acessorioIds, // Apenas IDs dos acessórios
        Long modeloCarroId, // Apenas o ID do modelo de carro
        Set<LocalDate> datasOcupadas)
{
    public CarroDTO(Long id, String placa, String chassi, String cor, String categoria, BigDecimal valorDiaria,
                    Set<Long> acessorioIds, Long modeloCarroId, Set<LocalDate> datasOcupadas) {
        this.id = id;
        this.placa = placa;
        this.chassi = chassi;
        this.cor = cor;
        this.categoria =  categoria;
        this.valorDiaria = valorDiaria;
        this.acessorioIds = acessorioIds;
        this.modeloCarroId = modeloCarroId;
        this.datasOcupadas = datasOcupadas;
    }

    public CarroDTO(String placa, String chassi, String cor, String categoria, BigDecimal valorDiaria, Set<Long> acessorioIds, Long modeloCarroId, Set<LocalDate> datasOcupadas) {
        this(null, placa, chassi, cor, categoria, valorDiaria, acessorioIds, modeloCarroId, datasOcupadas);
    }
}

