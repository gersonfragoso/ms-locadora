package com.solutis.locadora.stock_management.service;

import com.solutis.locadora.stock_management.dto.AcessorioDTO;

import java.util.List;

public interface AcessorioService {
    AcessorioDTO findById(Long id);
    List<AcessorioDTO> findAll();
    AcessorioDTO save(AcessorioDTO acessorioDTO);
    void delete(Long id);
}
