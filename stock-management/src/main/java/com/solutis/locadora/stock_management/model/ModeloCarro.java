package com.solutis.locadora.stock_management.model;

import com.solutis.locadora.stock_management.model.utils_enum.Categoria;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/*LOMBOK*/
@Data
@NoArgsConstructor
@AllArgsConstructor
/*JPA*/
@Entity
@Table(name="modelo")
public class ModeloCarro implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descricao", length = 100, nullable = false)
    private String descricao;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @OneToMany
    private List<Carro> carro;

    @ManyToOne
    @JoinColumn
    private Fabricante fabricante;
}