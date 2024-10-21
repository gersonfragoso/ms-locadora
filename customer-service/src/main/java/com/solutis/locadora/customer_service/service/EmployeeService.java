package com.solutis.locadora.customer_service.service;

import com.solutis.locadora.customer_service.dto.EmployeeRecordDto;
import com.solutis.locadora.customer_service.model.EmployeeModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeService {

    EmployeeModel createEmployee(EmployeeRecordDto employeeRecordDto);
    Optional<EmployeeModel> findById(UUID id);
    void deleteEmployee(UUID id);
    EmployeeModel updateEmployee(UUID id, EmployeeRecordDto employeeRecordDto);
    List<EmployeeModel> findAll();
}
