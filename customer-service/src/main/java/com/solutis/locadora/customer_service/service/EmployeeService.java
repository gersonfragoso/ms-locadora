package com.solutis.locadora.customer_service.service;

import com.solutis.locadora.customer_service.dto.EmployeeRecordDto;
import com.solutis.locadora.customer_service.mapper.Mapper;
import com.solutis.locadora.customer_service.model.EmployeeModel;
import com.solutis.locadora.customer_service.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService {

    final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public EmployeeModel createEmployee(EmployeeRecordDto employeeRecordDto){
        if (employeeRecordDto.person() == null) {
            throw new IllegalArgumentException("Os dados precisam ser preenchidos.");
        }
        EmployeeModel employeeModel = Mapper.toModel(employeeRecordDto);
        return employeeRepository.save(employeeModel);
    }

    public Optional<EmployeeModel> findById(UUID id){
        return employeeRepository.findById(id);
    }

    @Transactional
    public void deleteEmployee(UUID id){
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("Funcionário não encontrado");
        }
        employeeRepository.deleteById(id);
    }

    @Transactional
    public EmployeeModel updateEmployee(UUID id, EmployeeRecordDto employeeRecordDto){
        EmployeeModel existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
        EmployeeModel updateEmployee = Mapper.toModel(employeeRecordDto, existingEmployee);

        return employeeRepository.save(updateEmployee);
    }

    public List<EmployeeModel> findAll() {
        return employeeRepository.findAll();
    }
}
