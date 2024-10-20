package com.solutis.locadora.vehicle_catalog.service;

import com.solutis.locadora.vehicle_catalog.model.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
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
                .bodyToMono(new ParameterizedTypeReference<ResponseDTO<List<CarroDTO>>>() {})
                .flatMapMany(response -> {
                    List<CarroDTO> carros = response.getData();
                    return Flux.fromIterable(carros)
                            .flatMap(this::populateCarroExibicao)
                            .onErrorResume(e -> {
                                e.printStackTrace();
                                return Mono.empty();
                            });
                })
                .collectList();
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
                                FabricanteDTO fabricanteDTO = responseFabricante.getData();

                                Set<Long> acessorioIds = carroDTO.acessorioIds();
                                if (acessorioIds.isEmpty()) {
                                    System.out.println("Nenhum acessório encontrado para o carro ID: " + carroDTO.id());
                                }

                                return Flux.fromIterable(acessorioIds)
                                        .flatMap(id -> webClient.get()
                                                .uri("/acessorio/" + id)
                                                .retrieve()
                                                .bodyToMono(new ParameterizedTypeReference<ResponseDTO<AcessorioDTO>>() {})
                                                .doOnNext(responseAcessorio -> {
                                                })
                                                .map(responseAcessorio -> responseAcessorio.getData().descricao())) // Acessa o AcessorioDTO
                                        .collectList()
                                        .map(acessoriosNomes -> {
                                            return new CarroExibicaoDTO(
                                                    carroDTO.id(),
                                                    carroDTO.cor(),
                                                    carroDTO.valorDiaria(),
                                                    acessoriosNomes,
                                                    modeloCarroDTO.descricao(),
                                                    fabricanteDTO.nome(),
                                                    carroDTO.categoria().toString(),
                                                    carroDTO.datasOcupadas());
                                        });
                            });
                })
                .onErrorResume(e -> {
                    e.printStackTrace();
                    return Mono.empty();
                });
    }

    public Mono<List<CarroExibicaoDTO>> getCarrosByFabricante(Long fabricanteId) {
        return webClient.get()
                .uri("/carro")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ResponseDTO<List<CarroDTO>>>() {})
                .flatMapMany(response -> {
                    List<CarroDTO> carros = response.getData(); // Assume que getData() retorna uma lista
                    return Flux.fromIterable(carros)
                            .flatMap(carro -> isFabricanteDoCarro(carro, fabricanteId) // Verifica se o carro pertence ao fabricante de forma reativa
                                    .flatMap(isFabricante -> {
                                        if (isFabricante) {
                                            return populateCarroExibicao(carro); // Popula detalhes de cada carro
                                        } else {
                                            return Mono.empty(); // Se não for do fabricante, retorna vazio
                                        }
                                    }))
                            .onErrorResume(e -> {
                                // Tratamento de erro para o populate
                                e.printStackTrace(); // Log do erro
                                return Mono.empty(); // Retorna um Mono vazio em caso de erro
                            });
                })
                .collectList(); // Coleta tudo em uma lista
    }

    private Mono<Boolean> isFabricanteDoCarro(CarroDTO carro, Long fabricanteId) {
        return webClient.get()
                .uri("/modelocarro/" + carro.modeloCarroId())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ResponseDTO<ModeloCarroDTO>>() {})
                .map(responseModelo -> {
                    ModeloCarroDTO modeloCarroDTO = responseModelo.getData();
                    return modeloCarroDTO.fabricanteId().equals(fabricanteId);
                })
                .onErrorResume(e -> {
                    // Tratamento de erro ao recuperar o modelo
                    e.printStackTrace();
                    return Mono.just(false); // Em caso de erro, assume que não pertence ao fabricante
                });
    }

    public Mono<List<CarroExibicaoDTO>> getCarrosByCategoria(String categoria) {
        return webClient.get()
                .uri("/carro")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ResponseDTO<List<CarroDTO>>>() {})
                .flatMapMany(response -> {
                    List<CarroDTO> carros = response.getData();
                    return Flux.fromIterable(carros)
                            .filter(carro -> carro.categoria().equalsIgnoreCase(categoria)) // Filtra pela categoria
                            .flatMap(this::populateCarroExibicao)
                            .onErrorResume(e -> {
                                e.printStackTrace();
                                return Mono.empty();
                            });
                })
                .collectList();
    }

    public Mono<List<CarroExibicaoDTO>> getCarrosByAcessorios(List<Long> acessoriosIds) {
        return webClient.get()
                .uri("/carro")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ResponseDTO<List<CarroDTO>>>() {})
                .flatMapMany(response -> {
                    List<CarroDTO> carros = response.getData();
                    return Flux.fromIterable(carros)
                            .filter(carro -> carro.acessorioIds().stream()
                                    .anyMatch(acessorio -> acessoriosIds.contains(acessorio))) // Verifica se algum acessório do carro está na lista
                            .flatMap(this::populateCarroExibicao)
                            .onErrorResume(e -> {
                                e.printStackTrace();
                                return Mono.empty();
                            });
                })
                .collectList();
    }

    public Mono<List<CarroExibicaoDTO>> getCarrosByModelo(Long modeloId) {
        return webClient.get()
                .uri("/carro") // Endpoint para obter todos os carros
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ResponseDTO<List<CarroDTO>>>() {})
                .flatMapMany(response -> {
                    List<CarroDTO> carros = response.getData();
                    return Flux.fromIterable(carros)
                            .filter(carro -> carro.modeloCarroId().equals(modeloId))
                            .flatMap(this::populateCarroExibicao)
                            .onErrorResume(e -> {
                                e.printStackTrace();
                                return Mono.empty();
                            });
                })
                .collectList();
    }

    public Mono<List<CarroExibicaoDTO>> getCarrosDisponiveis(LocalDate dataInicio, LocalDate dataFim) {
        return webClient.get()
                .uri("/carro")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ResponseDTO<List<CarroDTO>>>() {})
                .flatMapMany(response -> {
                    List<CarroDTO> carros = response.getData();
                    return Flux.fromIterable(carros)
                            .filter(carro -> isCarroDisponivel(carro, dataInicio, dataFim)) // Verifica a disponibilidade
                            .flatMap(this::populateCarroExibicao)
                            .onErrorResume(e -> {
                                e.printStackTrace();
                                return Mono.empty();
                            });
                })
                .collectList();
    }

    private boolean isCarroDisponivel(CarroDTO carro, LocalDate dataInicio, LocalDate dataFim) {
        Set<LocalDate> datasOcupadas = carro.datasOcupadas();

        List<LocalDate> intervaloSolicitado = dataInicio.datesUntil(dataFim.plusDays(1)).collect(Collectors.toList());

        for (LocalDate data : intervaloSolicitado) {
            if (datasOcupadas.contains(data)) {
                return false;
            }
        }
        return true;
    }

}