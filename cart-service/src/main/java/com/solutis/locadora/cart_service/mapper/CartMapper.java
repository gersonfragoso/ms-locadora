package com.solutis.locadora.cart_service.mapper;


import com.solutis.locadora.cart_service.dto.CartDto;
import com.solutis.locadora.cart_service.dto.RentalDto;
import com.solutis.locadora.cart_service.model.CartModel;
import com.solutis.locadora.cart_service.model.RentalModel;

import java.util.stream.Collectors;

public class CartMapper {
    // Mapeia um DTO para Model
    public static RentalModel toRentalModel(RentalDto rentalDto) {
        RentalModel rentalModel = new RentalModel();
        rentalModel.setVehicleId(rentalDto.vehicleId());
        rentalModel.setRentalPrice(rentalDto.rentalPrice());
        return rentalModel;
    }

    public static CartModel toModel(CartDto cartDto) {
        CartModel cartModel = new CartModel();
        cartModel.setUserId(cartDto.userId());
        cartModel.setRentals(
                cartDto.rentals().stream()
                        .map(CartMapper::toRentalModel) // Mapeando os aluguéis (RentalDto -> RentalModel)
                        .collect(Collectors.toList())
        );
        return cartModel;
    }
}
