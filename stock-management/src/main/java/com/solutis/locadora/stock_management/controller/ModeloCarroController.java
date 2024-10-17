package com.solutis.locadora.stock_management.controller;


import com.solutis.locadora.stock_management.dto.ModeloCarroDTO;
import com.solutis.locadora.stock_management.dto.ResponseDTO;
import com.solutis.locadora.stock_management.service.service_impl.ModeloCarroServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/modelocarro")
public class ModeloCarroController {
    @Autowired
    private ModeloCarroServiceImpl modeloCarroService;

    @GetMapping
    public ResponseEntity<ResponseDTO<List<ModeloCarroDTO>>> findAll() {
        ResponseDTO<List<ModeloCarroDTO>> response;
        try {
            List<ModeloCarroDTO> modeloCarroDTOS = modeloCarroService.findAll();

            response = new ResponseDTO<>(HttpStatus.OK.value(),
                    "Lista de modelos de carros obtida com sucesso", modeloCarroDTOS);

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            response = new ResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Erro ao buscar modelos.", null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseDTO<ModeloCarroDTO>> findById(@PathVariable Long id){
        ResponseDTO<ModeloCarroDTO> response;
        try {
            ModeloCarroDTO modeloCarroEncontrado = modeloCarroService.findById(id);

            response = new ResponseDTO<>(HttpStatus.OK.value(),
                    "Resultado encontrado.", modeloCarroEncontrado);

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

    //OBS: O Json da criação não deve conter ID! Apenas descrição. Formato do Json p/ DTO:
    /*
        {
            "descricao": "string",
            "categoria": "enum",
            "fabricanteId": long
}
     */
    @PostMapping
    public ResponseEntity<ResponseDTO<ModeloCarroDTO>> insert (@RequestBody ModeloCarroDTO modeloCarroDTO){
        ResponseDTO<ModeloCarroDTO> response;
        try {
            ModeloCarroDTO savedModeloCarroDTO = modeloCarroService.save(modeloCarroDTO);

            response = new ResponseDTO<>(HttpStatus.CREATED.value(),
                    "Modelo de carro cadastrado com sucesso.",
                    savedModeloCarroDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IllegalArgumentException  e) {
            response = new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(),e.getMessage(),null);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        }
        catch (EntityNotFoundException e) {
            response = new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(),e.getMessage(),null);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        }
        catch (RuntimeException e) {
            response = new ResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Erro interno do servidor",null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteById(@PathVariable Long id) {
        try {
            modeloCarroService.delete(id);
            return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), "Modelo carro: (id: "+id+") deletado com sucesso", null));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro interno do servidor", null));
        }
    }
}
