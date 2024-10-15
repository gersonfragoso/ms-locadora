package com.solutis.locadora.customer_service.dto;

import com.solutis.locadora.customer_service.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PersonRecordDto(@NotBlank String name,
                              @NotNull LocalDate dateOfBirth,
                              @NotBlank String cpf,
                              @NotBlank @Email(message = "Formato de email inválido.") String email,
                              @NotNull Gender gender) {
}
