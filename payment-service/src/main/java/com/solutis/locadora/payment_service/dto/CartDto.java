package com.solutis.locadora.payment_service.dto;

import java.util.List;

public class CartDto {
    private Long userId;
    private List<RentalDto> rentals;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<RentalDto> getRentals() {
        return rentals;
    }

    public void setRentals(List<RentalDto> rentals) {
        this.rentals = rentals;
    }
}
