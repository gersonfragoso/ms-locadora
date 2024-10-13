package com.solutis.locadora.customer_service.driver;

import com.solutis.locadora.customer_service.person.PersonModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "DRIVER")
public class DriverModel extends PersonModel {

    @Column(length = 20, nullable = false, unique = true)
    private String cnh;
}