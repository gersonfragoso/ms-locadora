package com.solutis.locadora.stock_management.service;

import com.solutis.locadora.stock_management.dto.CarroDTO;

import java.util.List;

public interface CarroService {
    CarroDTO findById(Long id);
    List<CarroDTO> findAll();
    CarroDTO save(CarroDTO carro);
    //CarroDTO update(CarroDTO carro);
    void delete(Long id);
}
