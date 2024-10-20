package com.solutis.locadora.customer_service.service;

import com.solutis.locadora.customer_service.dto.EmployeeRecordDto;
import com.solutis.locadora.customer_service.mapper.Mapper;
import com.solutis.locadora.customer_service.model.EmployeeModel;
import com.solutis.locadora.customer_service.producer.NotificationProducer;
import com.solutis.locadora.customer_service.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService {

    final EmployeeRepository employeeRepository;

    private final NotificationProducer notificationProducer;

    public EmployeeService(EmployeeRepository employeeRepository, NotificationProducer notificationProducer) {
        this.employeeRepository = employeeRepository;
        this.notificationProducer = notificationProducer;
    }

    @Transactional
    public EmployeeModel createEmployee(EmployeeRecordDto employeeRecordDto){

        EmployeeModel employeeModel = Mapper.toModel(employeeRecordDto);
        EmployeeModel savedEmployee = employeeRepository.save(employeeModel);

        notificationProducer.sendNotification(savedEmployee.getName(), savedEmployee.getEmail());
        return savedEmployee;
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
