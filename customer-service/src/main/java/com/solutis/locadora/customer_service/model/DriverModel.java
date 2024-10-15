package com.solutis.locadora.customer_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "DRIVER")
public class DriverModel extends PersonModel {

    @Column(length = 20, nullable = false, unique = true)
    private String cnh;
}