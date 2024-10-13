package com.solutis.locadora.vehicle_rental_service.repository;

import com.solutis.locadora.vehicle_rental_service.domain.Aluguel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AluguelRepository extends JpaRepository<Aluguel, Long> {
}
