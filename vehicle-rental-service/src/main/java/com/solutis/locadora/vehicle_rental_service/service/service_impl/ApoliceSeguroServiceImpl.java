package com.solutis.locadora.vehicle_rental_service.service.service_impl;


import com.solutis.locadora.vehicle_rental_service.domain.ApoliceSeguro;
import com.solutis.locadora.vehicle_rental_service.dto.ApoliceSeguroDTO;
import com.solutis.locadora.vehicle_rental_service.repository.ApoliceSeguroRepository;
import com.solutis.locadora.vehicle_rental_service.service.ApoliceSeguroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApoliceSeguroServiceImpl implements ApoliceSeguroService {

    @Autowired
    private ApoliceSeguroRepository apoliceSeguroRepository;

    @Override
    public ApoliceSeguroDTO createApolice(ApoliceSeguro apoliceSeguro) {
        return null;
    }
}
