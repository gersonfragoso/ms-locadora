package com.solutis.locadora.vehicle_rental_service.service;

import com.solutis.locadora.vehicle_rental_service.domain.Aluguel;
import com.solutis.locadora.vehicle_rental_service.dto.AluguelDTO;

import java.util.List;

public interface AluguelService {
    public AluguelDTO createAluguel(Aluguel aluguel);
    public List<AluguelDTO> getAllAlugueis();
    public AluguelDTO updateAluguel(Long id, Aluguel aluguelAtualizado);
    public void deleteAluguel(Long id);
    public List<Object> getAluguelById(Long id);
}
