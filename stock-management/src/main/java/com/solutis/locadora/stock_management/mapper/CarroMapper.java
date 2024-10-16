package com.solutis.locadora.stock_management.mapper;

import com.solutis.locadora.stock_management.dto.CarroDTO;
import com.solutis.locadora.stock_management.model.Carro;
import com.solutis.locadora.stock_management.model.ModeloCarro;

public class CarroMapper {
    public static CarroDTO carroToDTO (Carro carro) {
        return new CarroDTO(
                carro.getId(),
                carro.getPlaca(),
                carro.getChassi(),
                carro.getCor(),
                carro.getValorDiaria(),
                carro.getModelo().getId());
    }

    public static Carro carroToEntity (CarroDTO carroDTO, ModeloCarro modeloCarro) {
        return new Carro(carroDTO.placa(), carroDTO.chassi(), carroDTO.cor(), carroDTO.valorDiaria(), modeloCarro);
    }


}
