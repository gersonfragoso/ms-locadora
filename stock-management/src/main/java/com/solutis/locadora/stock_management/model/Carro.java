package com.solutis.locadora.stock_management.model;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.tree.Tree;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

/*LOMBOK*/
@Data
@NoArgsConstructor
@AllArgsConstructor
/*JPA*/
@Entity
@Table(name = "carro")
public class Carro implements Serializable {

    /*public Carro(String placa, String chassi, String cor, BigDecimal valorDiaria, ModeloCarro modelo) {
        this.placa = placa;
        this.chassi = chassi;
        this.cor = cor;
        this.valorDiaria = valorDiaria;
        this.modelo = modelo;
    }*/

    public Carro(String placa, String chassi, String cor, BigDecimal valorDiaria, Set<Acessorio> acessorios, ModeloCarro modelo, Set<LocalDate> datasOcupacao) {
        this.placa = placa;
        this.chassi = chassi;
        this.cor = cor;
        this.valorDiaria = valorDiaria;
        this.acessorios = acessorios;
        this.modelo = modelo;
        this.datasOcupacao = datasOcupacao;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 8, unique = true)
    private String placa;

    @Column(nullable = false, length = 17, unique = true)
    private String chassi;

    @Column(nullable = false, length = 25)
    private String cor;

    @Column(name = "valor_diaria", nullable = false)
    private BigDecimal valorDiaria;

    @ManyToMany (fetch = FetchType.EAGER)
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
    private Set<LocalDate> datasOcupacao = new HashSet<>();


}