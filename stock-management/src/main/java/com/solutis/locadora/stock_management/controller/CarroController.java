package com.solutis.locadora.stock_management.controller;

import com.solutis.locadora.stock_management.dto.CarroDTO;
import com.solutis.locadora.stock_management.dto.ResponseDTO;
import com.solutis.locadora.stock_management.service.service_impl.CarroServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/carro")
public class CarroController {

    @Autowired
    private CarroServiceImpl carroService;

    @GetMapping
    public ResponseEntity<ResponseDTO<List<CarroDTO>>> findAll() {
        try {
            List<CarroDTO> carros = carroService.findAll();

            ResponseDTO<List<CarroDTO>> response = new ResponseDTO<>(
                    HttpStatus.OK.value(),
                    "Lista de carros obtida com sucesso",
                    carros);

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            ResponseDTO<List<CarroDTO>> errorResponse = new ResponseDTO<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Erro ao buscar carros",
                    null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseDTO<CarroDTO>> findById(@PathVariable Long id) {
        ResponseDTO<CarroDTO> response;
        try {
            CarroDTO carroEncontrado = carroService.findById(id);

            response = new ResponseDTO<>(HttpStatus.OK.value(),
                    "Resultado encontrado.", carroEncontrado);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (EntityNotFoundException e) {
            response = new ResponseDTO<>(HttpStatus.NOT_FOUND.value(),
                    e.getMessage(), null);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response = new ResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Erro interno do servidor.", null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /*JSON para testar a requisição post: modelo abaixo
    {
        "placa": "ABC-1234",
        "chassi": "1HGBH41JXMN109186",
        "cor": "Preto",
        "valorDiaria": 150.75,
        "acessorioIds": [8], // IDs dos acessórios relacionados
        "modeloCarroId": 1, // ID do modelo de carro
        "datasOcupadas": [] // Lista vazia para datas ocupadas
    }
     */

    @PostMapping
    public ResponseEntity<ResponseDTO<CarroDTO>> insert(@RequestBody CarroDTO carroDTO) {
        ResponseDTO<CarroDTO> response;
        try {
            CarroDTO savedCarroDTO = carroService.save(carroDTO);

            response = new ResponseDTO<>(HttpStatus.CREATED.value(),
                    "Carro cadastrado com sucesso.",
                    savedCarroDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IllegalArgumentException e) {
            response = new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(), null);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        } catch (RuntimeException e) {
            response = new ResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(), null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Endpoint para deletar um carro pelo ID
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteById(@PathVariable Long id) {
        try {
            carroService.delete(id);
            return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(),
                    "Carro (id: " + id + ") deletado com sucesso", null));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Erro interno do servidor", null));
        }
    }

    /*URL para fazer a requisição: carro/adicionarDataOcupacao?carroId=SEUIDAQUI&dataAlugado=AAAA-MM-DD */
    @PostMapping(value = "/adicionarDataOcupacao")
    public ResponseEntity<ResponseDTO<CarroDTO>> adicionarDataOcupacao(@RequestParam Long carroId,
                                                                       @RequestParam String dataAlugado) {
        ResponseDTO<CarroDTO> response;
        try {
            // Converter String para LocalDate
            String cleanedDataAlugado = dataAlugado.trim();
            LocalDate localDate = LocalDate.parse(dataAlugado);

            // Passa apenas o id do carro e a data para o serviço
            CarroDTO updatedCarroDTO = carroService.adicionarDataOcupacao(carroId, localDate);

            response = new ResponseDTO<>(HttpStatus.OK.value(),
                    "Data de ocupação adicionada com sucesso.", updatedCarroDTO);

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            response = new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        } catch (RuntimeException e) {
            response = new ResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Erro interno do servidor", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /*URL para fazer a requisição: carro/removerDataOcupacao?carroId=SEUIDAQUI&dataAlugado=AAAA-MM-DD */
    @PostMapping(value = "/removerDataOcupacao")
    public ResponseEntity<ResponseDTO<CarroDTO>> removerDataOcupacao(@RequestParam Long carroId,
                                                                     @RequestParam String dataAlugado) {
        ResponseDTO<CarroDTO> response;
        try {
            // Converter String para LocalDate
            String cleanedDataAlugado = dataAlugado.trim();
            LocalDate localDate = LocalDate.parse(dataAlugado);

            // Passa carroId e dataAlugado convertida para o serviço
            CarroDTO updatedCarroDTO = carroService.removerDataOcupacao(carroId, localDate);

            response = new ResponseDTO<>(HttpStatus.OK.value(),
                    "Data de ocupação removida com sucesso.", updatedCarroDTO);

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            response = new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        } catch (RuntimeException e) {
            response = new ResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Erro interno do servidor", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}


