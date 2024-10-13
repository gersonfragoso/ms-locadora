package com.solutis.locadora.vehicle_rental_service.dto;

import java.math.BigDecimal;

public record ApoliceSeguroDTO(
        BigDecimal valorFranquia,
        Boolean protecaoTerceiro,
        Boolean protecaoCausasNaturais,
        Boolean protecaoRoubo
) {}
