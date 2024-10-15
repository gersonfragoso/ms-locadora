package com.solutis.locadora.customer_service.service;

import com.solutis.locadora.customer_service.dto.DriverRecordDto;
import com.solutis.locadora.customer_service.mapper.Mapper;
import com.solutis.locadora.customer_service.model.DriverModel;
import com.solutis.locadora.customer_service.repository.DriverRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DriverService {

        final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Transactional
    public DriverModel createDriver(DriverRecordDto driverRecordDto){
        if (driverRecordDto.person() == null) {
            throw new IllegalArgumentException("Os dados precisam ser preenchidos.");
        }
        DriverModel driverModel = Mapper.toModel(driverRecordDto);
        return driverRepository.save(driverModel);
    }

    @Transactional
    public DriverModel updateDriver(UUID id, DriverRecordDto driverRecordDto){
        DriverModel existingDriver = driverRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Motorista não encontrado"));
        DriverModel updateDriver = Mapper.toModel(driverRecordDto, existingDriver);

        return driverRepository.save(updateDriver);
    }

    @Transactional
    public void deleteDriver(UUID id){
        if (!driverRepository.existsById(id)) {
            throw new RuntimeException("Motorista não encontrado");
        }
        driverRepository.deleteById(id);
    }

    public Optional<DriverModel> findById(UUID id){
        return driverRepository.findById(id);
    }

    public List<DriverModel> findAll() {
        return driverRepository.findAll();
    }
}
