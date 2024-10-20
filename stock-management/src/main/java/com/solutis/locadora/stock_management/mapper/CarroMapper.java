package com.solutis.locadora.stock_management.mapper;

import com.solutis.locadora.stock_management.dto.AcessorioDTO;
import com.solutis.locadora.stock_management.dto.CarroDTO;
import com.solutis.locadora.stock_management.dto.ModeloCarroDTO;
import com.solutis.locadora.stock_management.model.Acessorio;
import com.solutis.locadora.stock_management.model.Carro;
import com.solutis.locadora.stock_management.model.ModeloCarro;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CarroMapper {

    // Converte Carro Entity para DTO
    public static CarroDTO carroToDTO(Carro carro) {
        // Mapeia os IDs dos acessórios
        Set<Long> acessorioIds = carro.getAcessorios().stream()
                .map(Acessorio::getId)
                .collect(Collectors.toSet());
        System.out.println(carro.getAcessorios().toString());

        // Apenas o ID do modelo de carro
        Long modeloCarroId = carro.getModelo().getId();

        return new CarroDTO(
                carro.getId(),
                carro.getPlaca(),
                carro.getChassi(),
                carro.getCor(),
                carro.getValorDiaria(),
                acessorioIds,
                modeloCarroId,
                carro.getDatasOcupacao());
    }

    // Converte CarroDTO para Entity
    public static Carro carroToEntity(CarroDTO carroDTO, Set<Acessorio> acessorios, ModeloCarro modeloCarro) {


        return new Carro(
                carroDTO.placa(),
                carroDTO.chassi(),
                carroDTO.cor(),
                carroDTO.valorDiaria(),
                acessorios,
                modeloCarro,
                carroDTO.datasOcupadas());
    }
}

