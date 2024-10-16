package com.solutis.locadora.stock_management.repository;

import com.solutis.locadora.stock_management.model.Acessorio;
import com.solutis.locadora.stock_management.model.Carro;
import com.solutis.locadora.stock_management.model.ModeloCarro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Long> {
    boolean existsByPlacaIgnoreCase(String placa);
    boolean existsByChassisIgnoreCase(String chassis);
    List<Carro> findByModeloCarro(ModeloCarro modeloCarro);
    List<Carro> findByAcessoriosContaining(Acessorio acessorio);
}
