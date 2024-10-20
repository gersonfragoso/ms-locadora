package com.solutis.locadora.cart_service.service;

import com.solutis.locadora.cart_service.dto.RentalResponseDto;
import com.solutis.locadora.cart_service.model.CartModel;
import com.solutis.locadora.cart_service.model.RentalModel;
import reactor.core.publisher.Mono;

public interface CartService {

    void addRentalToCart(Long cartId, RentalModel rental);
    void removeRentalFromCart(Long cartId, Long rentalId);
    CartModel viewCart(Long cartId);
    Mono<String> checkout(Long cartId);
    void clearCart(Long cartId);
    Mono<RentalResponseDto> getRentalResponseById(Long rentalId);
}
