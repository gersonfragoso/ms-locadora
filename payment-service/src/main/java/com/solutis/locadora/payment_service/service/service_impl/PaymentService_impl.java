package com.solutis.locadora.payment_service.service.service_impl;

import com.solutis.locadora.payment_service.producer.NotificationProducer;
import com.solutis.locadora.payment_service.service.PaymentService;
import com.solutis.locadora.payment_service.dto.CartDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
public class PaymentService_impl implements PaymentService {

    @Value("${cart-service.url}")
    private String cartServiceUrl; // URL do serviço do carrinho

    @Value("${stock-management.url}")
    private String stockServiceUrl; // URL do serviço de estoque

    @Value("${vehicle-rental-service.url}")
    private String vehicleRentalServiceUrl;

    private final NotificationProducer notificationProducer; // Para envio de notificações
    private final WebClient webClient;

    public PaymentService_impl(NotificationProducer notificationProducer, WebClient.Builder webClientBuilder) {
        this.notificationProducer = notificationProducer;
        this.webClient = webClientBuilder.build();
    }

    // Método para buscar o carrinho pelo userId
    @Override
    public Mono<CartDto> getCart(Long userId) {
        return webClient.get()
                .uri(cartServiceUrl + "/cart/" + userId)
                .retrieve()
                .bodyToMono(CartDto.class)
                .doOnError(e -> {
                    throw new RuntimeException("Erro ao buscar carrinho: " + e.getMessage());
                });
    }

    // Método para bloquear o veículo no serviço de estoque
    public Mono<Void> reservarVeiculo(Long vehicleId, Date dataInicio, Date dataFim) {
        String estoqueUrl = vehicleRentalServiceUrl + "/reservar/" + vehicleId + "?inicio=" + dataInicio + "&fim=" + dataFim;
        return webClient.post()
                .uri(estoqueUrl)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnError(e -> {
                    throw new RuntimeException("Erro ao reservar veículo: " + e.getMessage());
                });
    }

    // Método para simular o processamento do pagamento
    private Mono<Void> processarPagamento(Long userId, double totalAmount) {
        return Mono.just(totalAmount)
                .doOnNext(amount -> {
                    if (amount <= 0) {
                        throw new RuntimeException("Valor de pagamento inválido.");
                    }
                    System.out.println("Pagamento de R$" + amount + " processado com sucesso para o usuário: " + userId);
                })
                .then();
    }

    // Método para processar a reserva de veículos e o pagamento
    @Override
    public Mono<Void> processarReserva(Long userId) {
        return getCart(userId)
                .flatMap(cart -> {
                    double totalAmount = calcularValorTotal(cart); // Método para calcular o valor total
                    return processarPagamento(userId, totalAmount) // Simula o pagamento
                            .then(
                                    Mono.when(
                                            cart.getRentals().stream()
                                                    .map(rental -> reservarVeiculo(rental.getVehicleId(), rental.getDataPedido(), rental.getDataDevolucao()))
                                                    .toArray(Mono[]::new)
                                    )
                            ).doOnSuccess(unused ->
                                    notificationProducer.sendNotification("Veículos reservados com sucesso para o usuário: " + userId)
                            );
                });
    }


    // Método para calcular o valor total do carrinho
    private double calcularValorTotal(CartDto cart) {
        return cart.getRentals().stream()
                .mapToDouble(rental -> rental.getValor()) // Supondo que RentalDto tenha um método getValor()
                .sum();
    }

    // Método para deletar o carrinho
    @Override
    public Mono<Void> deleteCart(Long userId) {
        return webClient.delete()
                .uri(cartServiceUrl + "/cart/" + userId)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnError(e -> {
                    throw new RuntimeException("Erro ao deletar carrinho: " + e.getMessage());
                });
    }
}
