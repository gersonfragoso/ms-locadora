package com.solutis.locadora.vehicle_rental_service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public record CarroExibicaoDTO(
        Long id,
        String cor,
        BigDecimal valorDiaria,
        List<String> acessoriosNomes,
        String modeloNome,
        String fabricanteNome,
        String categoria,
        Set<LocalDate> datasOcupadas) {

    public CarroExibicaoDTO(Long id, String cor, BigDecimal valorDiaria,
                            List<String> acessoriosNomes, String modeloNome,
                            String fabricanteNome, String categoria, Set<LocalDate> datasOcupadas) {
        this.id = id;
        this.cor = cor;
        this.valorDiaria = valorDiaria;
        this.acessoriosNomes = acessoriosNomes;
        this.modeloNome = modeloNome;
        this.fabricanteNome = fabricanteNome;
        this.categoria = categoria;
        this.datasOcupadas = datasOcupadas;
    }

}
