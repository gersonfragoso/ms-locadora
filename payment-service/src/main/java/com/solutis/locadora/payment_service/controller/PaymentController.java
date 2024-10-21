package com.solutis.locadora.payment_service.controller;

import com.solutis.locadora.payment_service.service.service_impl.PaymentService_impl;
import com.solutis.locadora.payment_service.dto.CartDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService_impl paymentService;

    public PaymentController(PaymentService_impl paymentService) {
        this.paymentService = paymentService;
    }

    // Endpoint para processar a reserva e o pagamento
    @PostMapping("/process-reservation/{userId}")
    public Mono<ResponseEntity<String>> processReservation(@PathVariable Long userId) {
        return paymentService.processarReserva(userId)
                .thenReturn(ResponseEntity.ok("Reserva e pagamento processados com sucesso!"))
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body("Erro ao processar reserva: " + e.getMessage())));
    }

    // Endpoint para deletar o carrinho manualmente, caso seja necessário
    @DeleteMapping("/cart/{userId}")
    public Mono<ResponseEntity<Void>> deleteCart(@PathVariable Long userId) {
        return paymentService.deleteCart(userId)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))  // Especifica Void explicitamente
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().<Void>build()));  // Especifica Void explicitamente
    }

    // Endpoint para visualizar o carrinho de um usuário
    @GetMapping("/cart/{userId}")
    public Mono<ResponseEntity<CartDto>> getCart(@PathVariable Long userId) {
        return paymentService.getCart(userId)
                .map(cart -> ResponseEntity.ok(cart))
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().build()));
    }
}