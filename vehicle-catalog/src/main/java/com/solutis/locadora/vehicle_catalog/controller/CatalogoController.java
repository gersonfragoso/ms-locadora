package com.solutis.locadora.vehicle_catalog.controller;


import com.solutis.locadora.vehicle_catalog.model.dto.CarroExibicaoDTO;
import com.solutis.locadora.vehicle_catalog.model.dto.ResponseDTO;
import com.solutis.locadora.vehicle_catalog.service.CarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/catalogo")
public class CatalogoController {

    @Autowired
    private CarroService carroService;

    @GetMapping("/carros")
    public Mono<ResponseDTO<List<CarroExibicaoDTO>>> getCarros() {
        return carroService.getAllCarros() // Obtém todos os CarroExibicaoDTO
                .map(carrosExibicao -> new ResponseDTO<>(HttpStatus.OK.value(), "Lista de carros obtida com sucesso.", carrosExibicao))
                .onErrorResume(e -> Mono.just(new ResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro ao obter carros", null)));
    }



    // Outros endpoints para filtros, etc.
}

