package com.example.frete.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tabela_frete")
@Getter
@Setter
public class TableFreightModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "faixa_peso")
    private String weightRange;
    @Column(name = "faixa_cep_inicio")
    private String cepStart;

    @Column(name = "faixa_cep_fim")
    private String cepEnd;

    @Column(name = "tarifa")
    private Double tariff;

    }