package com.solutis.locadora.vehicle_catalog.service;

import com.solutis.locadora.vehicle_catalog.model.dto.CarroDTO;
import com.solutis.locadora.vehicle_catalog.model.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class CarroService {
    @Autowired
    private WebClient webClient;

    public Mono<List<CarroDTO>> getAllCarros() {
        return webClient.get()
                .uri("/carro") // Exemplo de endpoint do stock-management
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ResponseDTO<List<CarroDTO>>>() {})
                .map(ResponseDTO::getData);
    }

}
