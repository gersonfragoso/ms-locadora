package com.solutis.locadora.vehicle_rental_service.dto;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public record AluguelDTO(
        Calendar dataPedido,
        Date dataEntrega,
        Date dataDevolucao,
        BigDecimal valorTotal
) {}
