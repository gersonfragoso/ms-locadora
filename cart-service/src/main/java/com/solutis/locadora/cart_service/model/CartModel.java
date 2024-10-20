package com.solutis.locadora.cart_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "cart")
public class CartModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RentalModel> rentals = new ArrayList<>();

    public void addRental(RentalModel rental) {
        rentals.add(rental);
    }

    public void removeRental(Long rentalId) {
        rentals.removeIf(rental -> rental.getId().equals(rentalId));
    }

    public BigDecimal calculateTotalPrice() {
        return rentals.stream()
                .map(RentalModel::getRentalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
