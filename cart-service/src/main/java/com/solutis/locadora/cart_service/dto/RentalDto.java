package com.solutis.locadora.cart_service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RentalDto (Long vehicleId,
                        BigDecimal rentalPrice){
}
