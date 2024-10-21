package com.solutis.locadora.cart_service.service.service_impl;


import com.solutis.locadora.cart_service.dto.PaymentRequestDto;
import com.solutis.locadora.cart_service.dto.RentalResponseDto;
import com.solutis.locadora.cart_service.model.CartModel;
import com.solutis.locadora.cart_service.model.RentalModel;
import com.solutis.locadora.cart_service.producer.NotificationProducer;
import com.solutis.locadora.cart_service.repository.CartRepository;
import com.solutis.locadora.cart_service.service.CartService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final WebClient webClient;
    private final NotificationProducer notificationProducer;
    @Value("${payment-service.url}")
    private String paymentServiceUrl;


    public CartServiceImpl(CartRepository cartRepository, WebClient webClient, NotificationProducer notificationProducer) {
        this.cartRepository = cartRepository;
        this.webClient = webClient;
        this.notificationProducer = notificationProducer;
    }

    // Adiciona um aluguel ao carrinho
    public void addRentalToCart(Long cartId, RentalModel rental) {
        CartModel cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Carrinho não encontrado"));
        cart.addRental(rental);
        cartRepository.save(cart);
    }

    // Remove um aluguel do carrinho
    public void removeRentalFromCart(Long cartId, Long rentalId) {
        CartModel cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Carrinho não encontrado"));
        cart.removeRental(rentalId);
        cartRepository.save(cart);
    }

    // Visualiza o carrinho
    public CartModel viewCart(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Carrinho não encontrado"));
    }

    // Realiza o checkout (inicia pagamento e limpa o carrinho)
    public Mono<String> checkout(Long cartId) {
        CartModel cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Carrinho não encontrado"));

        PaymentRequestDto paymentRequest = createPaymentRequest(cart);
        return webClient.post()
                .uri(paymentServiceUrl + "/process-payment")
                .bodyValue(paymentRequest)
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(response -> {
                    // Limpa o carrinho após pagamento
                    cartRepository.delete(cart);
                    notificationProducer.sendNotification("Pagamento realizado com sucesso para o carrinho " + cartId);
                })
                .onErrorResume(e -> {
                    return Mono.error(new RuntimeException("Erro ao processar pagamento: " + e.getMessage()));
                });
    }

    // Cria a requisição de pagamento
    private PaymentRequestDto createPaymentRequest(CartModel cart) {
    BigDecimal totalPrice = cart.calculateTotalPrice(); // Assuma que você tem esse método
    Long userId = cart.getUserId(); // Obtenha o ID do usuário associado ao carrinho
    return new PaymentRequestDto(totalPrice, userId);
    }

    public void clearCart(Long cartId) {
        CartModel cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Carrinho não encontrado"));
        cart.getRentals().clear();
        cartRepository.save(cart);
    }

    public Mono<RentalResponseDto> getRentalResponseById(Long rentalId) {
        return webClient.get()
                .uri("/" + rentalId)
                .retrieve()
                .bodyToMono(RentalResponseDto.class)
                .onErrorResume(e -> {
                    // Tratamento de erro
                    return Mono.error(new RuntimeException("Erro ao obter detalhes do aluguel: " + e.getMessage()));
                });
    }
}
