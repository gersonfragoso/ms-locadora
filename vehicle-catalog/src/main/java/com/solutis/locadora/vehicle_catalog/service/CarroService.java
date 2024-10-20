package com.solutis.locadora.vehicle_catalog.service;

import com.solutis.locadora.vehicle_catalog.model.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CarroService {

    @Autowired
    private WebClient webClient;

    public Mono<List<CarroExibicaoDTO>> getAllCarros() {
        return webClient.get()
                .uri("/carro") // Endpoint para recuperar os carros
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ResponseDTO<List<CarroDTO>>>() {}) // Ajuste o tipo
                .flatMapMany(response -> {
                    List<CarroDTO> carros = response.getData(); // Assume que getData() retorna uma lista
                    return Flux.fromIterable(carros)
                            .flatMap(this::populateCarroExibicao) // Popula detalhes de cada carro
                            .onErrorResume(e -> {
                                // Tratamento de erro para o populate
                                e.printStackTrace(); // Log do erro
                                return Mono.empty(); // Retorna um Mono vazio em caso de erro
                            });
                })
                .collectList(); // Coleta tudo em uma lista
    }

    private Mono<CarroExibicaoDTO> populateCarroExibicao(CarroDTO carroDTO) {
        return webClient.get()
                .uri("/modelocarro/" + carroDTO.modeloCarroId()) // Recupera o modelo do carro
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ResponseDTO<ModeloCarroDTO>>() {})
                .flatMap(responseModelo -> {
                    ModeloCarroDTO modeloCarroDTO = responseModelo.getData(); // Acessa o ModeloCarroDTO

                    return webClient.get()
                            .uri("/fabricante/" + modeloCarroDTO.fabricanteId()) // Recupera o fabricante
                            .retrieve()
                            .bodyToMono(new ParameterizedTypeReference<ResponseDTO<FabricanteDTO>>() {})
                            .flatMap(responseFabricante -> {
                                FabricanteDTO fabricanteDTO = responseFabricante.getData(); // Acessa o FabricanteDTO

                                // Verifique se há IDs de acessórios
                                Set<Long> acessorioIds = carroDTO.acessorioIds();
                                if (acessorioIds.isEmpty()) {
                                    System.out.println("Nenhum acessório encontrado para o carro ID: " + carroDTO.id());
                                }

                                return Flux.fromIterable(acessorioIds)
                                        .flatMap(id -> webClient.get()
                                                .uri("/acessorio/" + id) // Endpoint para acessar o acessório
                                                .retrieve()
                                                .bodyToMono(new ParameterizedTypeReference<ResponseDTO<AcessorioDTO>>() {})
                                                .doOnNext(responseAcessorio -> {
                                                    // Log da resposta do acessório
                                                    System.out.println("Acessório recuperado: " + responseAcessorio.getData());
                                                })
                                                .map(responseAcessorio -> responseAcessorio.getData().descricao())) // Acessa o AcessorioDTO
                                        .collectList()
                                        .map(acessoriosNomes -> {
                                            // Retornamos o CarroExibicaoDTO
                                            return new CarroExibicaoDTO(
                                                    carroDTO.id(),
                                                    carroDTO.cor(),
                                                    carroDTO.valorDiaria(),
                                                    acessoriosNomes,
                                                    modeloCarroDTO.descricao(),
                                                    fabricanteDTO.nome(),
                                                    carroDTO.datasOcupadas());
                                        });
                            });
                })
                .onErrorResume(e -> {
                    // Tratamento de erro para o populate
                    e.printStackTrace(); // Log do erro
                    return Mono.empty(); // Retorna um Mono vazio em caso de erro
                });
    }


}