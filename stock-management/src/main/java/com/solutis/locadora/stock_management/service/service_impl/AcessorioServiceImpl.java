package com.solutis.locadora.stock_management.service.service_impl;

import com.solutis.locadora.stock_management.dto.AcessorioDTO;
import com.solutis.locadora.stock_management.mapper.AcessorioMapper;
import com.solutis.locadora.stock_management.model.Acessorio;
import com.solutis.locadora.stock_management.repository.AcessorioRepository;
import com.solutis.locadora.stock_management.service.AcessorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class AcessorioServiceImpl implements AcessorioService {

    @Autowired
    private AcessorioRepository acessorioRepository;

    @Override
    public AcessorioDTO findById(Long id) {
        return acessorioRepository.findById(id)
                .map(AcessorioMapper::acessorioToDTO)
                .orElseThrow(() -> new RuntimeException("Acessório não encontrado com id: " + id));
    }


    @Override
    public List<AcessorioDTO> findAll() {
        try {
            return acessorioRepository.findAll().stream()
                    .map(AcessorioMapper::acessorioToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar acessórios", e);
        }
    }

    @Override
    public AcessorioDTO save(AcessorioDTO acessorioDTO) {
        if (acessorioDTO.id() != null) {
            throw new IllegalArgumentException("ID não deve ser fornecido para criação.");
        }
        try{
            Acessorio acessorio = AcessorioMapper.acessorioToEntity(acessorioDTO);
            Acessorio savedAcessorio = acessorioRepository.save(acessorio);
            return AcessorioMapper.acessorioToDTO(savedAcessorio);
        }
        catch(Exception e){
            throw new RuntimeException("Erro ao salvar acessório");
        }
    }

    @Override
    public void delete(Long id) {
        Acessorio acessorio = acessorioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Acessório não encontrado com id: " + id));
        acessorioRepository.delete(acessorio);
    }
}
