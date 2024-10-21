package com.solutis.locadora.vehicle_rental_service.service.service_impl;


import com.solutis.locadora.vehicle_rental_service.domain.Aluguel;
import com.solutis.locadora.vehicle_rental_service.dto.*;
import com.solutis.locadora.vehicle_rental_service.mapper.AluguelMapper;
import com.solutis.locadora.vehicle_rental_service.repository.AluguelRepository;
import com.solutis.locadora.vehicle_rental_service.repository.ApoliceSeguroRepository;
import com.solutis.locadora.vehicle_rental_service.service.AluguelService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AluguelServiceImpl implements AluguelService {

    @Autowired
    private AluguelRepository aluguelRepository;
    @Autowired
    private ApoliceSeguroRepository apoliceSeguroRepository;

    @Autowired
    private AluguelMapper aluguelMapper;

    @Autowired
    private WebClient webClient;

    @Override
    public AluguelDTO createAluguel(Aluguel aluguel) {
        try {
            if (aluguel.getApolice() != null && aluguel.getApolice().getId() != null) {
                Long apoliceId = aluguel.getApolice().getId();
            } else {
                throw new IllegalArgumentException("A apólice de seguro não foi fornecida ou está incompleta.");
            }
            Aluguel save = aluguelRepository.save(aluguel);
            return aluguelMapper.aluguelToDTO(save);
        }catch (DataAccessException e){
            throw new RuntimeException("Erro ao salvar: "+ e.getMessage(), e);
        }
    }
    @Override
    public List<AluguelDTO> getAllAlugueis() {
        List<Aluguel> alugueis = aluguelRepository.findAll();
        return alugueis.stream()
                .map(aluguelMapper::aluguelToDTO)
                .collect(Collectors.toList());
    }
    @Override
    public List<Object> getAluguelById(Long id) {
        // Primeiro, busque o aluguel pelo ID
        AluguelDTO aluguelDTO = aluguelRepository.findById(id)
                .map(aluguelMapper::aluguelToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Aluguel com ID " + id + " não encontrado"));

        // Agora, busque os detalhes completos do carro pelo carroId associado ao aluguel
        Mono<CarroExibicaoDTO> carroMono = getCarroById(aluguelDTO.carroId());

        // Sincroniza a chamada assíncrona e obtém o resultado
        CarroExibicaoDTO carroExibicaoDTO = carroMono.block();

        List<Object> response = new ArrayList<>();
        response.add(aluguelDTO);
        response.add(carroExibicaoDTO);
        return response;
    }

    @Override
    public AluguelDTO updateAluguel(Long id, Aluguel aluguelAtualizado) {
        Aluguel aluguelExistente = aluguelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aluguel com ID " + id + " não encontrado"));

        if (!aluguelExistente.getDataPedido().equals(aluguelAtualizado.getDataPedido())) {
            aluguelExistente.setDataPedido(aluguelAtualizado.getDataPedido());
        }
        if (!aluguelExistente.getDataEntrega().equals(aluguelAtualizado.getDataEntrega())) {
            aluguelExistente.setDataEntrega(aluguelAtualizado.getDataEntrega());
        }
        if (!aluguelExistente.getDataDevolucao().equals(aluguelAtualizado.getDataDevolucao())) {
            aluguelExistente.setDataDevolucao(aluguelAtualizado.getDataDevolucao());
        }
        if (!aluguelExistente.getValorTotal().equals(aluguelAtualizado.getValorTotal())) {
            aluguelExistente.setValorTotal(aluguelAtualizado.getValorTotal());
        }
        if (!aluguelExistente.getApolice().equals(aluguelAtualizado.getApolice())) {
            aluguelExistente.setApolice(aluguelAtualizado.getApolice());
        }
        if (!aluguelExistente.getMotoristaId().equals(aluguelAtualizado.getMotoristaId())) {
            aluguelExistente.setMotoristaId(aluguelAtualizado.getMotoristaId());
        }
        if (!aluguelExistente.getCarroId().equals(aluguelAtualizado.getCarroId())) {
            aluguelExistente.setCarroId(aluguelAtualizado.getCarroId());
        }

        Aluguel save = aluguelRepository.save(aluguelExistente);
        return aluguelMapper.aluguelToDTO(save);
    }
    @Override
    public void deleteAluguel(Long id) {
        Aluguel aluguel = aluguelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aluguel com ID " + id + " não encontrado"));

        aluguelRepository.delete(aluguel);
    }
    public Mono<CarroExibicaoDTO> getCarroById(Long carroId) {
        return webClient.get()
                .uri("/carro/" + carroId) // Define o endpoint para buscar o carro pelo ID
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ResponseDTO<CarroDTO>>() {})
                .flatMap(response -> {
                    CarroDTO carroDTO = response.getData();
                    return populateCarroExibicao(carroDTO); // Popula o DTO com os detalhes do carro
                })
                .onErrorResume(e -> {
                    e.printStackTrace();
                    return Mono.empty(); // Retorna um Mono vazio em caso de erro
                });
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

}
