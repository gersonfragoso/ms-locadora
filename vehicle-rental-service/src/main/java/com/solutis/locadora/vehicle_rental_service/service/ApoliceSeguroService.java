package com.solutis.locadora.vehicle_rental_service.service;

import com.solutis.locadora.vehicle_rental_service.domain.Aluguel;
import com.solutis.locadora.vehicle_rental_service.domain.ApoliceSeguro;
import com.solutis.locadora.vehicle_rental_service.dto.AluguelDTO;
import com.solutis.locadora.vehicle_rental_service.dto.ApoliceSeguroDTO;

import java.util.List;

public interface ApoliceSeguroService {
    public ApoliceSeguroDTO createApolice(ApoliceSeguro apoliceSeguro);
    public List<ApoliceSeguroDTO> getAllApolices() ;
    public ApoliceSeguroDTO getApoliceById(Long id);
    public ApoliceSeguroDTO updateApolice(Long id, ApoliceSeguro apoliceAtualizada);
    public void deleteApolice(Long id);
}
