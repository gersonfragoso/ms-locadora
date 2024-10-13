package com.solutis.locadora.customer_service.person;

import com.solutis.locadora.customer_service.gender.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PersonRecordDto(@NotBlank String name,
                              @NotNull LocalDate dateOfBirth,
                              @NotBlank String cpf,
                              @NotNull Gender gender) {
}
