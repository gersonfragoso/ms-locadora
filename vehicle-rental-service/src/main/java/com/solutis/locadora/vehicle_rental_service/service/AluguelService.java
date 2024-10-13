package com.solutis.locadora.vehicle_rental_service.service;

import com.solutis.locadora.vehicle_rental_service.domain.Aluguel;
import com.solutis.locadora.vehicle_rental_service.dto.AluguelDTO;

public interface AluguelService {
    public AluguelDTO createAluguel(Aluguel aluguel);
}
