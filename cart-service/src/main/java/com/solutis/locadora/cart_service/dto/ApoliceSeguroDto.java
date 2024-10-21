package com.solutis.locadora.cart_service.dto;

import java.math.BigDecimal;

public record ApoliceSeguroDto(BigDecimal valorFranquia,
                               Boolean protecaoTerceiro,
                               Boolean protecaoCausasNaturais,
                               Boolean protecaoRoubo) {
}
