package com.solutis.locadora.cart_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "rental")
public class RentalModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "vehicle_id", nullable = false)
    private Long vehicleId;
    @Column(name = "rental_price", nullable = false)
    private BigDecimal rentalPrice;
    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private CartModel cart;

}
