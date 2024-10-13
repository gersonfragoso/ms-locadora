package com.solutis.locadora.vehicle_rental_service.service;

import com.solutis.locadora.vehicle_rental_service.domain.Aluguel;
import com.solutis.locadora.vehicle_rental_service.domain.ApoliceSeguro;
import com.solutis.locadora.vehicle_rental_service.dto.AluguelDTO;
import com.solutis.locadora.vehicle_rental_service.dto.ApoliceSeguroDTO;

public interface ApoliceSeguroService {
    public ApoliceSeguroDTO createApolice(ApoliceSeguro apoliceSeguro);
}
