package com.solutis.locadora.customer_service.mapper;

import com.solutis.locadora.customer_service.dto.DriverRecordDto;
import com.solutis.locadora.customer_service.dto.EmployeeRecordDto;
import com.solutis.locadora.customer_service.model.DriverModel;
import com.solutis.locadora.customer_service.model.EmployeeModel;

public class Mapper {

    public static DriverModel toModel(DriverRecordDto driverRecordDto){
        if (driverRecordDto == null || driverRecordDto.person() == null) {
            throw new IllegalArgumentException("Motorista ou Pessoa não pode ser vazio");
        }

        DriverModel driverModel = new DriverModel();

        driverModel.setCnh(driverRecordDto.cnh());
        driverModel.setCpf(driverRecordDto.person().cpf());
        driverModel.setName(driverRecordDto.person().name());
        driverModel.setEmail(driverRecordDto.person().email());
        driverModel.setDateOfBirth(driverRecordDto.person().dateOfBirth());
        driverModel.setGender(driverRecordDto.person().gender());

        return driverModel;
    }

    public static EmployeeModel toModel(EmployeeRecordDto employeeRecordDto){
        if (employeeRecordDto == null || employeeRecordDto.person() == null) {
            throw new IllegalArgumentException("Funcionário ou Pessoa não pode ser vazio");
        }

        EmployeeModel employeeModel = new EmployeeModel();

        employeeModel.setEmployeeId(employeeRecordDto.employeeId());
        employeeModel.setCpf(employeeRecordDto.person().cpf());
        employeeModel.setName(employeeRecordDto.person().name());
        employeeModel.setEmail(employeeRecordDto.person().email());
        employeeModel.setDateOfBirth(employeeRecordDto.person().dateOfBirth());
        employeeModel.setGender(employeeRecordDto.person().gender());

        return employeeModel;
    }

    public static DriverModel toModel(DriverRecordDto driverRecordDto, DriverModel existingDriver){
        existingDriver.setCnh(driverRecordDto.cnh());
        existingDriver.setCpf(driverRecordDto.person().cpf());
        existingDriver.setName(driverRecordDto.person().name());
        existingDriver.setEmail(driverRecordDto.person().email());
        existingDriver.setDateOfBirth(driverRecordDto.person().dateOfBirth());
        existingDriver.setGender(driverRecordDto.person().gender());

        return existingDriver;
    }

    public static EmployeeModel toModel(EmployeeRecordDto employeeRecordDto, EmployeeModel existingEmployee){
        existingEmployee.setEmployeeId(employeeRecordDto.employeeId());
        existingEmployee.setCpf(employeeRecordDto.person().cpf());
        existingEmployee.setName(employeeRecordDto.person().name());
        existingEmployee.setEmail(employeeRecordDto.person().email());
        existingEmployee.setDateOfBirth(employeeRecordDto.person().dateOfBirth());
        existingEmployee.setGender(employeeRecordDto.person().gender());

        return existingEmployee;
    }
}
