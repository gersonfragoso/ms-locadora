package com.solutis.locadora.stock_management.service;

import com.solutis.locadora.stock_management.dto.ModeloCarroDTO;

import java.util.List;

public interface ModeloCarroService {
    ModeloCarroDTO findById(Long id);
    List<ModeloCarroDTO> findAll();
    ModeloCarroDTO save(ModeloCarroDTO modeloCarroDTO);
    //ModeloCarroDTO update(ModeloCarroDTO modeloCarroDTO);
    void delete(Long id);
}
