package com.solutis.locadora.vehicle_rental_service.service.service_impl;


import com.solutis.locadora.vehicle_rental_service.domain.Aluguel;
import com.solutis.locadora.vehicle_rental_service.dto.AluguelDTO;
import com.solutis.locadora.vehicle_rental_service.repository.AluguelRepository;
import com.solutis.locadora.vehicle_rental_service.service.AluguelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AluguelServiceImpl implements AluguelService {

    @Autowired
    private AluguelRepository aluguelRepository;

    @Override
    public AluguelDTO createAluguel(Aluguel aluguel) {
        return null;
    }
}
