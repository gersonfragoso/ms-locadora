package com.solutis.locadora.stock_management.repository;

import com.solutis.locadora.stock_management.model.Acessorio;
import com.solutis.locadora.stock_management.model.Carro;
import com.solutis.locadora.stock_management.model.ModeloCarro;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Long> {
    boolean existsByPlacaIgnoreCase(String placa);

    boolean existsByChassiIgnoreCase(String chassis);

    List<Carro> findByModelo(ModeloCarro modeloCarro);

    List<Carro> findByAcessoriosContaining(Acessorio acessorio);

    @Query("SELECT a FROM Acessorio a JOIN a.carros ca WHERE ca.id = :carroId")
    Set<Acessorio> findAcessoriosByCarroId(@Param("carroId") Long carroId);

    @Query("SELECT c FROM Carro c LEFT JOIN FETCH c.acessorios WHERE c.id = :id")
    Optional<Carro> findByIdWithAcessorios(Long id);

    @Query("SELECT c FROM Carro c LEFT JOIN FETCH c.acessorios")
    List<Carro> findAllWithAcessorios();

}