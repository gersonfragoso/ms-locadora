package com.solutis.locadora.vehicle_rental_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.solutis.locadora.vehicle_rental_service.domain.ApoliceSeguro;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;


public record AluguelDTO(Long Id,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") Calendar dataPedido,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") Date dataEntrega,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") Date dataDevolucao,
        BigDecimal valorTotal,
        ApoliceSeguro apolice,
        Long motoristaId,
        Long carroId
) {}