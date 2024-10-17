package com.solutis.locadora.stock_management.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/*LOMBOK*/
@Data
@NoArgsConstructor
@AllArgsConstructor
/*JPA*/
@Entity
@Table (name="fabricante")
public class Fabricante {

    public Fabricante(String nome) {
        this.nome = nome;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_fabricante", nullable = false,length = 100)
    private String nome;

    @OneToMany(mappedBy = "fabricante", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<ModeloCarro> modelosCarro = new HashSet<>();

}