package com.solutis.locadora.stock_management.service.service_impl;

import com.solutis.locadora.stock_management.dto.FabricanteDTO;
import com.solutis.locadora.stock_management.mapper.FabricanteMapper;
import com.solutis.locadora.stock_management.model.Fabricante;
import com.solutis.locadora.stock_management.repository.FabricanteRepository;
import com.solutis.locadora.stock_management.service.FabricanteService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FabricanteServiceImpl implements FabricanteService {

    @Autowired
    private FabricanteRepository fabricanteRepository;

    @Override
    public FabricanteDTO findById(Long id) {
        return fabricanteRepository.findById(id)
                .map(FabricanteMapper::fabricanteToDTO)
                .orElseThrow(()->new EntityNotFoundException("Fabricante não encontrado com id: "+id));
    }

    @Override
    public List<FabricanteDTO> findAll() {
        try{
            return fabricanteRepository.findAll()
                    .stream().map(FabricanteMapper::fabricanteToDTO)
                    .collect(Collectors.toList());
        }
        catch(Exception e){
            throw new RuntimeException("Erro ao buscar fabricantes.");
        }
    }

    @Override @Transactional
    public FabricanteDTO save(FabricanteDTO fabricanteDto) {
        if(fabricanteDto.id()!=null){
            throw new IllegalArgumentException("ID não deve ser fornecido para criação.");
        }
        try{
            Fabricante fabricante = FabricanteMapper.fabricanteToEntity(fabricanteDto);
            Fabricante savedFabricante = fabricanteRepository.save(fabricante);
            return FabricanteMapper.fabricanteToDTO(savedFabricante);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar fabricante.");
        }
    }

    @Override @Transactional
    public void delete(Long id) {
        Fabricante fabricante = fabricanteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fabricante não encontrado com id: " + id));
        fabricante.getModelosCarro().clear();
        fabricanteRepository.delete(fabricante);
        fabricanteRepository.flush();
    }
}
