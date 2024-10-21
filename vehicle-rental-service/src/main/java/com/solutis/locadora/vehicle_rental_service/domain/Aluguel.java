    package com.solutis.locadora.vehicle_rental_service.domain;

    import com.fasterxml.jackson.annotation.JsonBackReference;
    import com.fasterxml.jackson.annotation.JsonFormat;
    import com.solutis.locadora.vehicle_rental_service.dto.CarroExibicaoDTO;
    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.NoArgsConstructor;
    import org.antlr.v4.runtime.misc.NotNull;

    import java.math.BigDecimal;
    import java.util.Calendar;
    import java.util.Date;
    import lombok.Data;


    @Data
    @Entity
    @Table(name = "aluguel")
    @NoArgsConstructor
    @AllArgsConstructor
    public class Aluguel {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private Calendar dataPedido;

        @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private Date dataEntrega;

        @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private Date dataDevolucao;

        @NotNull
        private BigDecimal valorTotal;

        @OneToOne
        @JoinColumn(name = "apolice_id")
        @JsonBackReference
        private ApoliceSeguro apolice;

        @NotNull
        private Long motoristaId;

        @NotNull
        private Long carroId;

    }
