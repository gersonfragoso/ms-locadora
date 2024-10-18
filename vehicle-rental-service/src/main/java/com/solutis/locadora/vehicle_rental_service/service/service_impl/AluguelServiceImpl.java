package com.solutis.locadora.vehicle_rental_service.service.service_impl;


import com.solutis.locadora.vehicle_rental_service.domain.Aluguel;
import com.solutis.locadora.vehicle_rental_service.domain.ApoliceSeguro;
import com.solutis.locadora.vehicle_rental_service.dto.AluguelDTO;
import com.solutis.locadora.vehicle_rental_service.mapper.AluguelMapper;
import com.solutis.locadora.vehicle_rental_service.repository.AluguelRepository;
import com.solutis.locadora.vehicle_rental_service.repository.ApoliceSeguroRepository;
import com.solutis.locadora.vehicle_rental_service.service.AluguelService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.sql.SQLDataException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AluguelServiceImpl implements AluguelService {

    @Autowired
    private AluguelRepository aluguelRepository;
    @Autowired
    private ApoliceSeguroRepository apoliceSeguroRepository;

    @Autowired
    private AluguelMapper aluguelMapper;


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
    public AluguelDTO getAluguelById(Long id) {
        return aluguelRepository.findById(id)
                .map(aluguelMapper::aluguelToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Aluguel com ID " + id + " não encontrado"));
    }
    @Override
    public AluguelDTO updateAluguel(Long id, Aluguel aluguelAtualizado) {
        Aluguel aluguelExistente = aluguelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aluguel com ID " + id + " não encontrado"));

        aluguelExistente.setDataPedido(aluguelAtualizado.getDataPedido());
        aluguelExistente.setDataEntrega(aluguelAtualizado.getDataEntrega());
        aluguelExistente.setDataDevolucao(aluguelAtualizado.getDataDevolucao());
        aluguelExistente.setValorTotal(aluguelAtualizado.getValorTotal());
        aluguelExistente.setApolice(aluguelAtualizado.getApolice());
        aluguelExistente.setMotoristaId(aluguelAtualizado.getMotoristaId());
        aluguelExistente.setCarroId(aluguelAtualizado.getCarroId());

        Aluguel save = aluguelRepository.save(aluguelExistente);
        return aluguelMapper.aluguelToDTO(save);
    }
    @Override
    public void deleteAluguel(Long id) {
        Aluguel aluguel = aluguelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aluguel com ID " + id + " não encontrado"));

        aluguelRepository.delete(aluguel);
    }

}
