package com.solutis.locadora.cart_service.dto;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public record RentalResponseDto(Calendar dataPedido,
                                Date dataEntrega,
                                Date dataDevolucao,
                                BigDecimal valorTotal,
                                ApoliceSeguroDto apoliceSeguro){


}