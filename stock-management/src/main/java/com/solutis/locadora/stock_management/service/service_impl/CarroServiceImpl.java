package com.solutis.locadora.stock_management.service.service_impl;

import com.solutis.locadora.stock_management.dto.CarroDTO;
import com.solutis.locadora.stock_management.mapper.CarroMapper;
import com.solutis.locadora.stock_management.model.Acessorio;
import com.solutis.locadora.stock_management.model.Carro;
import com.solutis.locadora.stock_management.model.ModeloCarro;
import com.solutis.locadora.stock_management.repository.AcessorioRepository;
import com.solutis.locadora.stock_management.repository.CarroRepository;
import com.solutis.locadora.stock_management.repository.ModeloCarroRepository;
import com.solutis.locadora.stock_management.service.CarroService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class CarroServiceImpl implements CarroService {

    @Autowired
    private CarroRepository carroRepository;

    @Autowired
    private ModeloCarroRepository modeloCarroRepository;

    @Autowired
    private AcessorioRepository acessorioRepository;

    @Override
    @Transactional
    public CarroDTO findById(Long id) {
        return carroRepository.findById(id)
                .map(CarroMapper::carroToDTO)
                .orElseThrow(() -> new RuntimeException("Não existe um carro cadastrado com o id: " + id));
    }

    @Override
    @Transactional
    public List<CarroDTO> findAll() {
        return carroRepository.findAll()
                .stream().map(CarroMapper::carroToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CarroDTO save(CarroDTO carroDTO) {
        try {
            // Busca o modelo de carro pelo ID presente no DTO
            ModeloCarro modeloCarro = modeloCarroRepository.findById(carroDTO.modeloCarroId())
                    .orElseThrow(() -> new RuntimeException("Modelo não cadastrado"));

            // Busca os acessórios pelo conjunto de IDs presentes no DTO
            Set<Acessorio> acessorios = carroDTO.acessorioIds().stream()
                    .map(acessorioId -> acessorioRepository.findById(acessorioId)
                            .orElseThrow(() -> new RuntimeException("Acessório não encontrado com o id: " + acessorioId)))
                    .collect(Collectors.toSet());
            // Converte o DTO para entidade passando o modelo e os acessórios
            Carro carro = CarroMapper.carroToEntity(carroDTO, acessorios, modeloCarro);

            // Validações de placa e chassi
            if (carro.getPlaca() == null || (!isPlacaComumValida(carro.getPlaca()) && !isPlacaMercosulValida(carro.getPlaca()))) {
                throw new IllegalArgumentException("Placa do carro inválida!");
            }
            if (carro.getChassi() == null || !isChassiValido(carro.getChassi())) {
                throw new IllegalArgumentException("Chassi inválido!");
            }
            if (carroRepository.existsByPlacaIgnoreCase(carro.getPlaca())) {
                throw new IllegalArgumentException("Placa do carro já existente no sistema!");
            }
            if (carroRepository.existsByChassiIgnoreCase(carro.getChassi())) {
                throw new IllegalArgumentException("Número de chassi já existente no sistema!");
            }
            // Salva o carro e retorna o DTO
            return CarroMapper.carroToDTO(carroRepository.save(carro));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Carro carro = carroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carro não encontrado com o id: " + id));
        try {
            carroRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<CarroDTO> findCarrosByModelo(ModeloCarro modeloCarro) {
        return carroRepository.findByModelo(modeloCarro)
                .stream().map(CarroMapper::carroToDTO)
                .collect(Collectors.toList());
    }

    public List<CarroDTO> findCarrosByAcessorio(Acessorio acessorio) {
        return carroRepository.findByAcessoriosContaining(acessorio)
                .stream().map(CarroMapper::carroToDTO)
                .collect(Collectors.toList());
    }

    private boolean isPlacaComumValida(String placa) {
        String placaPadrao = "^[A-Z]{3}-?\\d{4}$";
        return placa.toUpperCase().matches(placaPadrao);
    }

    private boolean isPlacaMercosulValida(String placa) {
        String placaPadrao = "^[A-Z]{3}\\d[A-Z]{2}\\d{2}$";
        return placa.toUpperCase().matches(placaPadrao);
    }

    private boolean isChassiValido(String chassi) {
        String chassiPadrao = "^[A-HJ-NPR-Z0-9]{17}$";
        return chassi.toUpperCase().matches(chassiPadrao);
    }


    @Transactional
    public CarroDTO adicionarDataOcupacao(Long carroId, LocalDate dataAlugado) {
        Carro carro = carroRepository.findById(carroId)
                .orElseThrow(() -> new EntityNotFoundException("Carro não encontrado"));

        if (carro.getDatasOcupacao().contains(dataAlugado)) {
            throw new IllegalArgumentException("Carro não disponível na data solicitada.");
        }

        carro.getDatasOcupacao().add(dataAlugado);
        return CarroMapper.carroToDTO(carroRepository.save(carro));
    }


    @Transactional
    public CarroDTO removerDataOcupacao(Long carroId, LocalDate dataAlugado) {
        Carro carro = carroRepository.findById(carroId)
                .orElseThrow(() -> new EntityNotFoundException("Carro não encontrado"));

        if (!carro.getDatasOcupacao().contains(dataAlugado)) {
            throw new IllegalArgumentException("Carro não estava reservado na data solicitada para exclusão.");
        }

        carro.getDatasOcupacao().remove(dataAlugado);
        return CarroMapper.carroToDTO(carroRepository.save(carro));
    }
}

