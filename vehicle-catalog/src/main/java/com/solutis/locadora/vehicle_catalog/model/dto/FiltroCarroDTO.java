package com.solutis.locadora.vehicle_catalog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FiltroCarroDTO {
    private String modelo;
    private String fabricante;
    private List<LocalDate> datasDisponiveis;
    private List<String> acessorioNomes;

}
