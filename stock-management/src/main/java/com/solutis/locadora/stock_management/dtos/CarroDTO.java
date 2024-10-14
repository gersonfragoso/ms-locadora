package com.solutis.locadora.stock_management.dtos;

import java.math.BigDecimal;
import java.util.Set;

public record CarroDTO( Long id,
                        String placa,
                        String chassi,
                        String cor,
                        BigDecimal valorDiaria,
                        Long modeloId,
                        Set<AcessorioDTO> acessorioDTOS ) {
}
