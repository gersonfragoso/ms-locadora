package com.solutis.locadora.stock_management.controller;

import com.solutis.locadora.stock_management.dto.FabricanteDTO;
import com.solutis.locadora.stock_management.dto.ResponseDTO;
import com.solutis.locadora.stock_management.service.service_impl.FabricanteServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fabricante")
public class FabricanteController {

    @Autowired
    private FabricanteServiceImpl fabricanteService;

    @GetMapping
    public ResponseEntity<ResponseDTO<List<FabricanteDTO>>> findAll() {
        ResponseDTO<List<FabricanteDTO>> response;
        try {
            List<FabricanteDTO> fabricantes = fabricanteService.findAll();

            response = new ResponseDTO<>(HttpStatus.OK.value(),
                    "Lista de fabricantes obtida com sucesso", fabricantes);

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            response = new ResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Erro ao buscar fabricantes.", null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseDTO<FabricanteDTO>> findById(@PathVariable Long id){
        ResponseDTO<FabricanteDTO> response;
        try {
            FabricanteDTO acessorioEncontrado = fabricanteService.findById(id);

            response = new ResponseDTO<>(HttpStatus.OK.value(),
                    "Resultado encontrado.", acessorioEncontrado);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (EntityNotFoundException e){
            response = new ResponseDTO<>(HttpStatus.NOT_FOUND.value(),
                    e.getMessage() ,null);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        catch (Exception e){
            response = new ResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Erro interno do servidor." ,null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    //OBS: O Json da criação não deve conter ID! Apenas nome do fabricante. Formato do Json p/ DTO:
    /*
        {
            "nome": "nome do fabricante"
        }
     */
    @PostMapping
    public ResponseEntity<ResponseDTO<FabricanteDTO>> insert (@RequestBody FabricanteDTO fabricanteDTO){
        ResponseDTO<FabricanteDTO> response;
        try {
            FabricanteDTO savedFabricanteDTO = fabricanteService.save(fabricanteDTO);

            response = new ResponseDTO<>(HttpStatus.OK.value(),
                    "Fabricante cadastrado com sucesso.",
                    savedFabricanteDTO);

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException  e) {
            response = new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(),e.getMessage(),null);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        } catch (RuntimeException e) {
            response = new ResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Erro interno do servidor",null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteById(@PathVariable Long id) {
        try {
            fabricanteService.delete(id);
            return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), "Fabricante (id: "+id+") deletado com sucesso", null));
        }
        catch (EntityNotFoundException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseDTO<>(HttpStatus.NOT_FOUND.value(),
                            e.getMessage(), null));

        }
        catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Erro interno do servidor", null));
        }
    }
}
