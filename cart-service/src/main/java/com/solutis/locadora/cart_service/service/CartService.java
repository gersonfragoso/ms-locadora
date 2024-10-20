package com.solutis.locadora.cart_service.service;


import com.solutis.locadora.cart_service.dto.RentalResponseDto;
import com.solutis.locadora.cart_service.model.CartModel;
import com.solutis.locadora.cart_service.model.RentalModel;
import com.solutis.locadora.cart_service.producer.NotificationProducer;
import com.solutis.locadora.cart_service.repository.CartRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final RestTemplate restTemplate;
    private final NotificationProducer notificationProducer;
   // @Value("${payment-service.url}")
   // private String paymentServiceUrl;

    public CartService(CartRepository cartRepository, RestTemplate restTemplate, NotificationProducer notificationProducer) {
        this.cartRepository = cartRepository;
        this.restTemplate = restTemplate;
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
    public String checkout(Long cartId) {
        CartModel cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Carrinho não encontrado"));

        /*

        PaymentRequest paymentRequest = createPaymentRequest(cart);
        try {
            String paymentResponse = restTemplate.postForObject(paymentServiceUrl + "/process-payment", paymentRequest, String.class);

            // Limpa o carrinho após pagamento
            cartRepository.delete(cart);
            notificationProducer.sendNotification("Pagamento realizado com sucesso para o carrinho " + cartId);
            return "Aluguéis finalizados com sucesso!";
        } catch (Exception e) {
            throw new IllegalStateException("Erro ao processar pagamento: " + e.getMessage());
        }
         */
        return "";
    }

    /*

    // Cria a requisição de pagamento
    private PaymentRequest createPaymentRequest(CartModel cart) {
        BigDecimal totalPrice = cart.calculateTotalPrice();
        Long userId = cart.getUserId(); // Obtenha o ID do usuário associado ao carrinho
        return new PaymentRequest(totalPrice, userId);
    }
     */

    public void clearCart(Long cartId) {
        CartModel cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Carrinho não encontrado"));
        cart.getRentals().clear();
        cartRepository.save(cart);
    }

    public RentalResponseDto getRentalResponseById(Long rentalId) {
        String rentalServiceUrl = "http://localhost:8080/aluguel/" + rentalId;
        return restTemplate.getForObject(rentalServiceUrl, RentalResponseDto.class);
    }
}
