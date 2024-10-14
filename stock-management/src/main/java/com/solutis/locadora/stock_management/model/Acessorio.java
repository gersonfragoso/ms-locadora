package com.solutis.locadora.stock_management.model;

import com.solutis.locadora.stock_management.model.Carro;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
/*LOMBOK*/
@Data
@NoArgsConstructor
@AllArgsConstructor
/*JPA*/
@Entity
@Table (name = "acessorio")
public class Acessorio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false, length = 100)
    private String descricao;

    @ManyToMany(mappedBy = "acessorios")
    private Set<Carro> carros = new HashSet<>();
}