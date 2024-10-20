package com.solutis.locadora.cart_service.repository;

import com.solutis.locadora.cart_service.model.CartModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<CartModel, Long> {
}
