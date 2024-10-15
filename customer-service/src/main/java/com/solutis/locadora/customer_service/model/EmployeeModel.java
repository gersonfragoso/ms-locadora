package com.solutis.locadora.customer_service.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "EMPLOYEE")
public class EmployeeModel extends PersonModel {

    @Column(length = 20, nullable = false, unique = true)
    private String employeeId;
}
