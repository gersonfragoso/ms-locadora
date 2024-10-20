package com.solutis.locadora.customer_service.controller;

import com.solutis.locadora.customer_service.dto.DriverRecordDto;
import com.solutis.locadora.customer_service.model.DriverModel;
import com.solutis.locadora.customer_service.service.DriverService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/driver")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping
    public ResponseEntity<DriverModel> createDriver(@RequestBody @Valid DriverRecordDto driverRecordDto){
        DriverModel driverModel = driverService.createDriver(driverRecordDto);
        return ResponseEntity.ok(driverModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DriverModel> updateDriver(@PathVariable UUID id,
                                                    @RequestBody @Valid DriverRecordDto driverRecordDto){
    DriverModel updatedDriver = driverService.updateDriver(id, driverRecordDto);
    return  ResponseEntity.ok(updatedDriver);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverModel> getDriverById(@PathVariable UUID id){
        Optional<DriverModel> driverModel = driverService.findById(id);
        return driverModel.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable UUID id) {
        driverService.deleteDriver(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<DriverModel>> getAllDrivers() {
        List<DriverModel> drivers = driverService.findAll();
        return ResponseEntity.ok(drivers);
    }
}
