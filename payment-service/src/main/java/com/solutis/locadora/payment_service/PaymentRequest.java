package com.solutis.locadora.payment_service;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {
    private BigDecimal totalAmount;
    private Long userId;

    public PaymentRequest(BigDecimal totalAmount, Long userId) {
        this.totalAmount = totalAmount;
        this.userId = userId;
    }
}