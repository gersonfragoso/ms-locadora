package com.solutis.locadora.vehicle_rental_service.mapper;

import com.solutis.locadora.vehicle_rental_service.domain.Aluguel;
import com.solutis.locadora.vehicle_rental_service.dto.AluguelDTO;
import org.springframework.stereotype.Component;

@Component
public class AluguelMapper {
    public AluguelDTO aluguelToDTO(Aluguel aluguel){
        return new AluguelDTO(
                aluguel.getDataPedido(),
                aluguel.getDataEntrega(),
                aluguel.getDataDevolucao(),
                aluguel.getValorTotal()
        );
    }
}
