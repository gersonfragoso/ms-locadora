package com.solutis.locadora.stock_management.controller;

import com.solutis.locadora.stock_management.dto.AcessorioDTO;
import com.solutis.locadora.stock_management.dto.ResponseDTO;
import com.solutis.locadora.stock_management.service.service_impl.AcessorioServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@RestController
@RequestMapping("/acessorio")
public class AcessorioController {
    @Autowired
    private AcessorioServiceImpl acessorioService;

    @GetMapping
    public ResponseEntity<ResponseDTO<List<AcessorioDTO>>> findAll() {
        try {
            List<AcessorioDTO> acessorios = acessorioService.findAll();

            ResponseDTO<List<AcessorioDTO>> response = new ResponseDTO<>(
                    HttpStatus.OK.value(),
                    "Lista de acessórios obtida com sucesso",
                    acessorios);

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            ResponseDTO<List<AcessorioDTO>> errorResponse = new ResponseDTO<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Erro ao buscar acessórios",
                    null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseDTO<AcessorioDTO>> findById(@PathVariable Long id){
        ResponseDTO<AcessorioDTO> response;
        try {
            AcessorioDTO acessorioEncontrado = acessorioService.findById(id);

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

    //OBS: O Json da criação não deve conter ID! Apenas descrição. Formato do Json p/ DTO:
    /*
        {
            "descricao": "descricao do acessorio"
        }
     */
    @PostMapping
    public ResponseEntity<ResponseDTO<AcessorioDTO>> insert (@RequestBody AcessorioDTO acessorioDTO){
        ResponseDTO<AcessorioDTO> response;
        try {
            AcessorioDTO savedAcessorioDTO = acessorioService.save(acessorioDTO);

            response = new ResponseDTO<>(HttpStatus.OK.value(),
                    "Item cadastrado com sucesso.",
                    savedAcessorioDTO);

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
            acessorioService.delete(id);
            return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), "Acessório (id: "+id+") deletado com sucesso", null));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro interno do servidor", null));
        }
    }




}
