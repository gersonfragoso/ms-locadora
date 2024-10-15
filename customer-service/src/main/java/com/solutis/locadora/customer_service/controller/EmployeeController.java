package com.solutis.locadora.customer_service.controller;

import com.solutis.locadora.customer_service.dto.EmployeeRecordDto;
import com.solutis.locadora.customer_service.model.EmployeeModel;
import com.solutis.locadora.customer_service.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<EmployeeModel> createEmployee(@RequestBody @Valid EmployeeRecordDto employeeRecordDto) {
        EmployeeModel employeeModel = employeeService.createEmployee(employeeRecordDto);
        return ResponseEntity.ok(employeeModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeModel> updateEmployee(@PathVariable UUID id,
                                                        @RequestBody @Valid EmployeeRecordDto employeeRecordDto) {
        EmployeeModel updatedEmployee = employeeService.updateEmployee(id, employeeRecordDto);
        return ResponseEntity.ok(updatedEmployee);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeModel> getEmployeeById(@PathVariable UUID id) {
        Optional<EmployeeModel> employeeModel = employeeService.findById(id);
        return employeeModel.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable UUID id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<EmployeeModel>> getAllEmployees() {
        List<EmployeeModel> employees = employeeService.findAll();
        return ResponseEntity.ok(employees);
    }
}
