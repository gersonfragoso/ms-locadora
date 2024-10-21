package com.solutis.locadora.vehicle_rental_service.mapper;

import com.solutis.locadora.vehicle_rental_service.domain.Aluguel;
import com.solutis.locadora.vehicle_rental_service.dto.AluguelDTO;
import com.solutis.locadora.vehicle_rental_service.dto.CarroExibicaoDTO;
import org.springframework.stereotype.Component;

@Component
public class AluguelMapper {

    public AluguelDTO aluguelToDTO(Aluguel aluguel){
        return new AluguelDTO(
                aluguel.getId(),
                aluguel.getDataPedido(),
                aluguel.getDataEntrega(),
                aluguel.getDataDevolucao(),
                aluguel.getValorTotal(),
                aluguel.getApolice(),
                aluguel.getMotoristaId(),
                aluguel.getCarroId());
    }
}
