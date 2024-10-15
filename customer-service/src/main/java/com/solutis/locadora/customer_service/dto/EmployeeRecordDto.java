package com.solutis.locadora.customer_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EmployeeRecordDto(@NotBlank String employeeId,
                                @NotNull PersonRecordDto person) {
}
