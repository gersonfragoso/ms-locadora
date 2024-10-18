package com.solutis.locadora.vehicle_rental_service.controller;


import com.solutis.locadora.vehicle_rental_service.domain.ApoliceSeguro;
import com.solutis.locadora.vehicle_rental_service.dto.ApoliceSeguroDTO;
import com.solutis.locadora.vehicle_rental_service.service.ApoliceSeguroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apolice")
public class ApoliceSeguroController {

    @Autowired
    private ApoliceSeguroService apoliceSeguroService;

    @PostMapping("/create")
    public ResponseEntity<ApoliceSeguroDTO> createApolice(@RequestBody ApoliceSeguro apoliceSeguro) {
        ApoliceSeguroDTO createdApolice = apoliceSeguroService.createApolice(apoliceSeguro);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdApolice);
    }

    @GetMapping("/list")
    public List<ApoliceSeguroDTO> listApolices() {
        return apoliceSeguroService.getAllApolices();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApoliceSeguroDTO> getApoliceById(@PathVariable Long id) {
        ApoliceSeguroDTO apoliceDTO = apoliceSeguroService.getApoliceById(id);
        return ResponseEntity.ok(apoliceDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApoliceSeguroDTO> updateApolice(@PathVariable Long id, @RequestBody ApoliceSeguro apoliceAtualizada) {
        ApoliceSeguroDTO updatedApolice = apoliceSeguroService.updateApolice(id, apoliceAtualizada);
        return ResponseEntity.ok(updatedApolice);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteApolice(@PathVariable Long id) {
        apoliceSeguroService.deleteApolice(id);
        return ResponseEntity.noContent().build();
    }
}
