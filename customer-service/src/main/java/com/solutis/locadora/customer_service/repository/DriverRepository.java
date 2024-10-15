package com.solutis.locadora.customer_service.repository;

import com.solutis.locadora.customer_service.model.DriverModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface DriverRepository extends JpaRepository<DriverModel, UUID> {
}
