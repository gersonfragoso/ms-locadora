package com.solutis.locadora.customer_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DriverRecordDto(@NotBlank String cnh,
                              @NotNull PersonRecordDto person) {
}
