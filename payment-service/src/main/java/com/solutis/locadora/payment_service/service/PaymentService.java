package com.solutis.locadora.payment_service.service;

import com.solutis.locadora.payment_service.dto.CartDto;
import reactor.core.publisher.Mono;

import java.util.Date;

public interface PaymentService {

    Mono<CartDto> getCart(Long userId);
    Mono<Void> reservarVeiculo(Long vehicleId, Date dataInicio, Date dataFim);
    Mono<Void> processarReserva(Long userId);
    Mono<Void> deleteCart(Long userId);
}
