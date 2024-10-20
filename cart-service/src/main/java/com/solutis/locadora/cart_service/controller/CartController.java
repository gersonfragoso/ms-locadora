package com.solutis.locadora.cart_service.controller;

import com.solutis.locadora.cart_service.dto.RentalResponseDto;
import com.solutis.locadora.cart_service.model.CartModel;
import com.solutis.locadora.cart_service.model.RentalModel;
import com.solutis.locadora.cart_service.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/{cartId}/add")
    public ResponseEntity<String> addRentalToCart(@PathVariable Long cartId, @RequestBody RentalModel rental) {
        cartService.addRentalToCart(cartId, rental);
        return ResponseEntity.ok("Aluguel adicionado ao carrinho.");
    }

    @PostMapping("/{cartId}/checkout")
    public ResponseEntity<String> checkout(@PathVariable Long cartId) {
        String response = cartService.checkout(cartId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{cartId}/remove/{rentalId}")
    public ResponseEntity<String> removeRentalFromCart(@PathVariable Long cartId, @PathVariable Long rentalId) {
        cartService.removeRentalFromCart(cartId, rentalId);
        return ResponseEntity.ok("Aluguel removido do carrinho.");
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartModel> viewCart(@PathVariable Long cartId) {
        CartModel cart = cartService.viewCart(cartId);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<String> clearCart(@PathVariable Long cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.ok("Carrinho esvaziado.");
    }

    @GetMapping("/rentals/{rentalId}/details")
    public ResponseEntity<RentalResponseDto> getRentalResponse(@PathVariable Long rentalId) {
        RentalResponseDto rentalResponse = cartService.getRentalResponseById(rentalId);
        return ResponseEntity.ok(rentalResponse);
    }
}
