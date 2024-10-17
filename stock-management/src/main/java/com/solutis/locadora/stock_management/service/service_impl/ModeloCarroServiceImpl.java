package com.solutis.locadora.stock_management.service.service_impl;

import com.solutis.locadora.stock_management.dto.FabricanteDTO;
import com.solutis.locadora.stock_management.dto.ModeloCarroDTO;
import com.solutis.locadora.stock_management.mapper.ModeloCarroMapper;
import com.solutis.locadora.stock_management.model.Fabricante;
import com.solutis.locadora.stock_management.model.ModeloCarro;
import com.solutis.locadora.stock_management.model.utils_enum.Categoria;
import com.solutis.locadora.stock_management.repository.FabricanteRepository;
import com.solutis.locadora.stock_management.repository.ModeloCarroRepository;
import com.solutis.locadora.stock_management.service.ModeloCarroService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModeloCarroServiceImpl implements ModeloCarroService {

    @Autowired
    private ModeloCarroRepository modeloCarroRepository;

    @Autowired
    private FabricanteRepository fabricanteRepository;

    @Override
    public ModeloCarroDTO findById(Long id) {
        return modeloCarroRepository.findById(id)
                .map(ModeloCarroMapper::modeloCarroToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Modelo de carro não encontrado com o id: " + id));
    }

    @Override
    public List<ModeloCarroDTO> findAll() {
        return modeloCarroRepository.findAll()
                .stream()
                .map(ModeloCarroMapper::modeloCarroToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ModeloCarroDTO save(ModeloCarroDTO modeloCarroDTO) {
        // Busca o fabricante pelo ID presente no DTO
        Fabricante fabricante = fabricanteRepository.findById(modeloCarroDTO.fabricanteId())
                .orElseThrow(() -> new EntityNotFoundException("Fabricante inexistente na base de dados."));

        try {
            // Converte o DTO para a entidade passando o fabricante buscado
            ModeloCarro modeloCarro = ModeloCarroMapper.modeloCarroToEntity(modeloCarroDTO, fabricante);

            // Salva a entidade no banco
            ModeloCarro modeloCarroSaved = modeloCarroRepository.save(modeloCarro);

            // Retorna o DTO do modelo salvo
            return ModeloCarroMapper.modeloCarroToDTO(modeloCarroSaved);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar o modelo de carro", e);
        }
    }

    @Override
    public void delete(Long id) {
        ModeloCarro modeloCarro = modeloCarroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Modelo de carro não encontrado com o id: " + id));

        modeloCarroRepository.delete(modeloCarro);
    }
}

