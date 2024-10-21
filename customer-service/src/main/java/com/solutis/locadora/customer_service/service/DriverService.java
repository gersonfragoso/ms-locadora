package com.solutis.locadora.customer_service.service;

import com.solutis.locadora.customer_service.dto.DriverRecordDto;
import com.solutis.locadora.customer_service.model.DriverModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DriverService {

    DriverModel createDriver(DriverRecordDto driverRecordDto);
    DriverModel updateDriver(UUID id, DriverRecordDto driverRecordDto);
    void deleteDriver(UUID id);
    Optional<DriverModel> findById(UUID id);
    List<DriverModel> findAll();



}
