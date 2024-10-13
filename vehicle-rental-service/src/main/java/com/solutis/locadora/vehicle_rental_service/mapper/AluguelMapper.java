package com.solutis.locadora.vehicle_rental_service.mapper;

import com.solutis.locadora.vehicle_rental_service.domain.Aluguel;
import com.solutis.locadora.vehicle_rental_service.dto.AluguelDTO;

public class AluguelMapper {
    public static AluguelDTO aluguelToDTO(Aluguel aluguel){
        return new AluguelDTO(
                aluguel.getDataPedido(),
                aluguel.getDataEntrega(),
                aluguel.getDataDevolucao(),
                aluguel.getValorTotal()
        );
    }
}
