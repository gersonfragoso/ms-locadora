package com.solutis.locadora.vehicle_catalog.controller;


import com.solutis.locadora.vehicle_catalog.model.dto.CarroExibicaoDTO;
import com.solutis.locadora.vehicle_catalog.model.dto.ResponseDTO;
import com.solutis.locadora.vehicle_catalog.service.CarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/catalogo")
public class CatalogoController {

    @Autowired
    private CarroService carroService;

    @GetMapping("/carros")
    public Mono<ResponseDTO<List<CarroExibicaoDTO>>> getAllCarros() {
        return carroService.getAllCarros()
                .map(carrosExibicao -> new ResponseDTO<>(HttpStatus.OK.value(), "Lista de carros obtida com sucesso.", carrosExibicao))
                .onErrorResume(e -> Mono.just(new ResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro ao obter carros", null)));
    }

    //FILTRO FABRICANTE - ID
    @GetMapping("/carros/fabricante/{fabricanteId}")
    public Mono<ResponseEntity<List<CarroExibicaoDTO>>> getCarrosByFabricante(@PathVariable Long fabricanteId) {
        return carroService.getCarrosByFabricante(fabricanteId)
                .map(carros -> {
                    if (!carros.isEmpty()) {
                        return new ResponseEntity<>(carros, HttpStatus.OK); // Se houver carros, retorna OK
                    } else {
                        return new ResponseEntity<List<CarroExibicaoDTO>>(HttpStatus.NOT_FOUND); // Se não houver carros, retorna 404
                    }
                })
                .onErrorResume(e -> {
                    e.printStackTrace();
                    return Mono.just(new ResponseEntity<List<CarroExibicaoDTO>>(HttpStatus.INTERNAL_SERVER_ERROR)); // Em caso de erro, retorna 500
                });
    }

    //FILTRO CATEGORIA - String na URI
    //como usar
    //URI : /carros/categoria?categoria=SUACATEGORIAAQUI
    @GetMapping("/carros/categoria")
    public Mono<ResponseEntity<List<CarroExibicaoDTO>>> getCarrosByCategoria(
            @RequestParam(value = "categoria") String categoria) {

        return carroService.getCarrosByCategoria(categoria)
                .map(carros -> {
                    if (!carros.isEmpty()) {
                        return new ResponseEntity<>(carros, HttpStatus.OK);
                    } else {
                        return new ResponseEntity<List<CarroExibicaoDTO>>(HttpStatus.NOT_FOUND);
                    }
                })
                .onErrorResume(e -> {
                    e.printStackTrace();
                    return Mono.just(new ResponseEntity<List<CarroExibicaoDTO>>(HttpStatus.INTERNAL_SERVER_ERROR));
                });
    }

    //POR ACESSORIO - IDS
    //URI: /carros/acessorios?acessorios=1,2,3 Obs: pode passar mais de um acessório se quiser.
    @GetMapping("/carros/acessorios")
    public Mono<ResponseEntity<List<CarroExibicaoDTO>>> getCarrosByAcessorios(
            @RequestParam(value = "acessorios") List<Long> acessoriosIds) {

        return carroService.getCarrosByAcessorios(acessoriosIds)
                .map(carros -> {
                    if (!carros.isEmpty()) {
                        return new ResponseEntity<>(carros, HttpStatus.OK);
                    } else {
                        return new ResponseEntity<List<CarroExibicaoDTO>>(HttpStatus.NOT_FOUND);
                    }
                })
                .onErrorResume(e -> {
                    e.printStackTrace();
                    return Mono.just(new ResponseEntity<List<CarroExibicaoDTO>>(HttpStatus.INTERNAL_SERVER_ERROR));
                });
    }

    //POR MODELO - ID
    //URI: /carros/modelo?modeloId=10
    @GetMapping("/carros/modelo")
    public Mono<ResponseEntity<List<CarroExibicaoDTO>>> getCarrosByModelo(
            @RequestParam(value = "modeloId") Long modeloId) {

        return carroService.getCarrosByModelo(modeloId)
                .map(carros -> {
                    if (!carros.isEmpty()) {
                        return new ResponseEntity<>(carros, HttpStatus.OK);
                    } else {
                        return new ResponseEntity<List<CarroExibicaoDTO>>(HttpStatus.NOT_FOUND);
                    }
                })
                .onErrorResume(e -> {
                    e.printStackTrace();
                    return Mono.just(new ResponseEntity<List<CarroExibicaoDTO>>(HttpStatus.INTERNAL_SERVER_ERROR));
                });
    }

    //POR DATA DISPONÍVEL
    //URI: /carros/disponiveis?dataInicio=2024-10-01&dataFim=2024-10-10
    //FORMATO: AAAA-MM-DD
    @GetMapping("/carros/disponiveis")
    public Mono<ResponseEntity<List<CarroExibicaoDTO>>> getCarrosDisponiveis(
            @RequestParam(value = "dataInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(value = "dataFim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {

        return carroService.getCarrosDisponiveis(dataInicio, dataFim)
                .map(carros -> {
                    if (!carros.isEmpty()) {
                        return new ResponseEntity<>(carros, HttpStatus.OK);
                    } else {
                        return new ResponseEntity<List<CarroExibicaoDTO>>(HttpStatus.NOT_FOUND);
                    }
                })
                .onErrorResume(e -> {
                    e.printStackTrace();
                    return Mono.just(new ResponseEntity<List<CarroExibicaoDTO>>(HttpStatus.INTERNAL_SERVER_ERROR));
                });
    }

}

