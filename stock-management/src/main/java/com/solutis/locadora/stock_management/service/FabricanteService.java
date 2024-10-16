package com.solutis.locadora.stock_management.service;

import com.solutis.locadora.stock_management.dto.FabricanteDTO;

import java.util.List;

public interface FabricanteService {

    FabricanteDTO findById(Long id);
    List<FabricanteDTO> findAll();
    FabricanteDTO save(FabricanteDTO fabricanteDto);
    void delete(Long id);
}
