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

    @Column(name = "weight_range")
    private String weightRange = "";
    @Column(name = "cep_start")
    private String cepStart = "";

    @Column(name = "cep_end")
    private String cepEnd = "";

    @Column(name = "tariff")
    private Double tariff = 0.0;

    }