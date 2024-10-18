package com.solutis.locadora.vehicle_rental_service.service.service_impl;


import com.solutis.locadora.vehicle_rental_service.domain.ApoliceSeguro;
import com.solutis.locadora.vehicle_rental_service.dto.ApoliceSeguroDTO;
import com.solutis.locadora.vehicle_rental_service.mapper.ApoliceSeguroMapper;
import com.solutis.locadora.vehicle_rental_service.repository.ApoliceSeguroRepository;
import com.solutis.locadora.vehicle_rental_service.service.ApoliceSeguroService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApoliceSeguroServiceImpl implements ApoliceSeguroService {

    @Autowired
    private ApoliceSeguroRepository apoliceSeguroRepository;
    @Autowired
    private ApoliceSeguroMapper apoliceSeguroMapper;

    @Override
    public ApoliceSeguroDTO createApolice(ApoliceSeguro apoliceSeguro) {
        try {
            ApoliceSeguro save = apoliceSeguroRepository.save(apoliceSeguro);
            return apoliceSeguroMapper.apoliceSeguroToDTO(save);
        } catch (DataAccessException e) {
            throw new RuntimeException("Erro ao salvar: " + e.getMessage(), e);
        }
    }
    @Override
    public List<ApoliceSeguroDTO> getAllApolices() {
        List<ApoliceSeguro> apolices = apoliceSeguroRepository.findAll();
        return apolices.stream()
                .map(apoliceSeguroMapper::apoliceSeguroToDTO)
                .collect(Collectors.toList());
    }
    @Override
    public ApoliceSeguroDTO getApoliceById(Long id) {
        return apoliceSeguroRepository.findById(id)
                .map(apoliceSeguroMapper::apoliceSeguroToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Apólice com ID " + id + " não encontrada"));
    }
    @Override
    public ApoliceSeguroDTO updateApolice(Long id, ApoliceSeguro apoliceAtualizada) {
        ApoliceSeguro apoliceExistente = apoliceSeguroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Apólice com ID " + id + " não encontrada"));

        // Atualizando os campos
        apoliceExistente.setValorFranquia(apoliceAtualizada.getValorFranquia());
        apoliceExistente.setProtecaoTerceiro(apoliceAtualizada.getProtecaoTerceiro());
        apoliceExistente.setProtecaoCausasNaturais(apoliceAtualizada.getProtecaoCausasNaturais());
        apoliceExistente.setProtecaoRoubo(apoliceAtualizada.getProtecaoRoubo());

        ApoliceSeguro save = apoliceSeguroRepository.save(apoliceExistente);
        return apoliceSeguroMapper.apoliceSeguroToDTO(save);
    }
    @Override
    public void deleteApolice(Long id) {
        ApoliceSeguro apolice = apoliceSeguroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Apólice com ID " + id + " não encontrada"));
        apoliceSeguroRepository.delete(apolice);
    }
}
