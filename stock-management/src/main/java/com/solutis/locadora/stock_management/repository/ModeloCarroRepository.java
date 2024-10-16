package com.solutis.locadora.stock_management.repository;

import com.solutis.locadora.stock_management.model.ModeloCarro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeloCarroRepository extends JpaRepository<ModeloCarro, Long> {
}
