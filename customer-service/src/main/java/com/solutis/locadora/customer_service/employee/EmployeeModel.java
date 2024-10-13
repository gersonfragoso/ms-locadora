package com.solutis.locadora.customer_service.employee;

import com.solutis.locadora.customer_service.person.PersonModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "EMPLOYEE")
public class EmployeeModel extends PersonModel {

    @Column(length = 20, nullable = false, unique = true)
    private String employeeId;
}
