package com.solutis.locadora.stock_management.model;

import jakarta.persistence.*;
import lombok.*;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*LOMBOK*/
@Data
@NoArgsConstructor
@AllArgsConstructor
/*JPA*/
@Entity
@Table(name = "carro")
public class Carro implements Serializable {

    public Carro(String placa, String chassi, String cor, BigDecimal valorDiaria, ModeloCarro modelo) {
        this.placa = placa;
        this.chassi = chassi;
        this.cor = cor;
        this.valorDiaria = valorDiaria;
        this.modelo = modelo;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 7, unique = true)
    private String placa;

    @Column(nullable = false, length = 17, unique = true)
    private String chassi;

    @Column(nullable = false, length = 25)
    private String cor;

    @Column(name = "valor_diaria", nullable = false)
    private BigDecimal valorDiaria;

    @ManyToMany
    @JoinTable(
            name = "carro_acessorio",
            joinColumns = @JoinColumn(name = "carro_id"),
            inverseJoinColumns = @JoinColumn(name = "acessorio_id")
    )
    private Set<Acessorio> acessorios = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "modelo_id", nullable = false)
    private ModeloCarro modelo;

    @ElementCollection
    @CollectionTable(name = "ocupacoes_carro", joinColumns = @JoinColumn(name = "carro_id"))
    @Column(name = "data_ocupacao")
    private List<LocalDate> datasOcupacao = new ArrayList<>();


}