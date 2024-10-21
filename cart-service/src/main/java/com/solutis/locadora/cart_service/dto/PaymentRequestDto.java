package com.solutis.locadora.cart_service.dto;

import java.math.BigDecimal;

public record PaymentRequestDto(BigDecimal totalPrice, Long userId) {
}
