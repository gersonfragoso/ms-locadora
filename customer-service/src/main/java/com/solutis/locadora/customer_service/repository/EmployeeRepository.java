package com.solutis.locadora.customer_service.repository;

import com.solutis.locadora.customer_service.model.EmployeeModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<EmployeeModel, UUID> {
}
