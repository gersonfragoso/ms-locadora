package com.solutis.locadora.vehicle_rental_service.controller;


import com.solutis.locadora.vehicle_rental_service.domain.Aluguel;
import com.solutis.locadora.vehicle_rental_service.dto.AluguelDTO;
import com.solutis.locadora.vehicle_rental_service.service.AluguelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aluguel")
public class AluguelController {

    @Autowired
    private AluguelService aluguelService;

    @PostMapping("/create")
    public ResponseEntity<AluguelDTO> createAluguel(@RequestBody Aluguel aluguel) {
        AluguelDTO createdAluguel = aluguelService.createAluguel(aluguel);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAluguel);
    }

    @GetMapping
    public List<AluguelDTO> listAlugueis() {
        return aluguelService.getAllAlugueis();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAluguelById(@PathVariable Long id) {
        List <Object>aluguelDTO = aluguelService.getAluguelById(id);
        return ResponseEntity.ok(aluguelDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AluguelDTO> updateAluguel(@PathVariable Long id, @RequestBody Aluguel aluguelAtualizado) {
        AluguelDTO updatedAluguel = aluguelService.updateAluguel(id, aluguelAtualizado);
        return ResponseEntity.ok(updatedAluguel);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAluguel(@PathVariable Long id) {
        aluguelService.deleteAluguel(id);
        return ResponseEntity.noContent().build();
    }
}

