package com.solutis.locadora.stock_management.model;

import jakarta.persistence.*;
import lombok.*;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
/*LOMBOK*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
/*JPA*/
@Entity
@Table (name="carro")
public class Carro implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "placa", nullable = false, length = 7, unique = true)
    private String placa;

    @Column(name = "chassi", nullable = false, length = 17, unique = true )
    private String chassi;

    @Column(name = "cor", nullable = false, length =25)
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



}
