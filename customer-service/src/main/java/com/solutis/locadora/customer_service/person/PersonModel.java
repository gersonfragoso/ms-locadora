package com.solutis.locadora.customer_service.person;

import com.solutis.locadora.customer_service.gender.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@MappedSuperclass
public abstract class PersonModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private LocalDate dateOfBirth;
    @Column(length = 11, nullable = false, unique = true)
    private String cpf;

    @Enumerated(EnumType.STRING)
    private Gender gender;
}
