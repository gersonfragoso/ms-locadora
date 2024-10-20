package com.solutis.locadora.customer_service.service;

import com.solutis.locadora.customer_service.dto.DriverRecordDto;
import com.solutis.locadora.customer_service.mapper.Mapper;
import com.solutis.locadora.customer_service.model.DriverModel;
import com.solutis.locadora.customer_service.producer.NotificationProducer;
import com.solutis.locadora.customer_service.repository.DriverRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DriverService {

        final DriverRepository driverRepository;
        private final NotificationProducer notificationProducer;

    public DriverService(DriverRepository driverRepository, NotificationProducer notificationProducer) {
        this.driverRepository = driverRepository;
        this.notificationProducer = notificationProducer;
    }

    @Transactional
    public DriverModel createDriver(DriverRecordDto driverRecordDto){

        DriverModel driverModel = Mapper.toModel(driverRecordDto);
        DriverModel savedDriver = driverRepository.save(driverModel);

        // Envie a notificação ao Notification Service
        notificationProducer.sendNotification(savedDriver.getName(), savedDriver.getEmail());

        return savedDriver;
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
