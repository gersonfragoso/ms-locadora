package com.solutis.locadora.vehicle_rental_service.mapper;

import com.solutis.locadora.vehicle_rental_service.domain.ApoliceSeguro;
import com.solutis.locadora.vehicle_rental_service.dto.ApoliceSeguroDTO;
import org.springframework.stereotype.Component;

@Component
public class ApoliceSeguroMapper {
    public ApoliceSeguroDTO apoliceSeguroToDTO(ApoliceSeguro apolice){
        return new ApoliceSeguroDTO(
                apolice.getValorFranquia(),
                apolice.getProtecaoTerceiro(),
                apolice.getProtecaoCausasNaturais(),
                apolice.getProtecaoRoubo()
        );
    }
}
